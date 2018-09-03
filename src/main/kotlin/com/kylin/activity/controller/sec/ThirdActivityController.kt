package com.kylin.activity.controller.sec

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult
import com.github.binarywang.wxpay.exception.WxPayException
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityTicket
import com.kylin.activity.databases.tables.pojos.ActivityUser
import com.kylin.activity.model.ActivityAttendInfo
import com.kylin.activity.model.ActivityScoreInfo
import com.kylin.activity.service.CommunityService
import com.kylin.activity.service.ThirdActivityService
import com.kylin.activity.service.UserService
import com.kylin.activity.service.WxService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

data class ThirdActivityPublishData(
        var activity: Activity? = null,
        var tickets: List<ActivityTicket>? = null,
        var attendInfos: List<ActivityAttendInfo>? = null,
        var canAttend: Boolean = false,
        var activityType: Int? = 0,
        var scoreInfos: ActivityScoreInfo? = null
)

/**
 * 第三方团体组织活动管理控制器
 * @author Richard C. Hu
 */
@Controller
@RequestMapping("sec/community/thirdactivity")
class ThirdActivityController : BaseController() {

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 第三方团体组织活动服务
     */
    @Autowired
    private val thirdActivityService: ThirdActivityService? = null

    /**
     * 微信接口服务
     */
    @Autowired
    private val wxService: WxService? = null

    /**
     * 团体组织服务
     */
    @Autowired
    private val communityService: CommunityService? = null
    /**
     * 第三方活动管理之
     * 查询活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/activities", method = [RequestMethod.POST, RequestMethod.GET])
    fun activities(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)

        return "sec/community/thirdactivity/activities"
    }

    /**
     *
     * 第三方活动管理之
     * 异步查询活动信息
     * @param map: 查询条件参数
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getActivities", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getActivities(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var status = map["status"]
        var tags = map["tags"]
        var title = map["title"]
        var activityId = map["id"]
        var items = thirdActivityService!!.getAllActivityUserItemsAndCommunity(start, end, activityId, title, tags, status, this.sessionCommunity.id)
        return items.intoMaps()
    }


    /**
     * 第三方最新活动
     * 之编辑（id:Int）和添加(type:Int)活动信息
     * @param id: 活动ID
     * @param type: 活动类型
     * @param model: 模型数据
     * @return 发布或编辑活动视图页面
     */
    @RequestMapping("/publish", method = [RequestMethod.GET])
    fun publish(@RequestParam(required = false) id: Int?, @RequestParam(required = false) type: Int?, model: Model): String {

        var data = ThirdActivityPublishData()
        if (id != null && id > 0) {
            data.activity = thirdActivityService!!.getActivity(id)
            data.tickets = thirdActivityService!!.getActivityTickets(id)
            val mapper = jacksonObjectMapper()
            data.attendInfos = mapper.readValue<List<ActivityAttendInfo>>(data.activity!!.attendInfos)
            data.canAttend = data.activity!!.endTime == data.activity!!.attendDueTime
            data.activityType = data.activity!!.activityType
            if (data.activity!!.scoreInfos != null && data.activity!!.scoreInfos != "") {
                data.scoreInfos = mapper.readValue<ActivityScoreInfo>(data.activity!!.scoreInfos)
            } else {
                data.scoreInfos = ActivityScoreInfo()
            }
        } else {
            data.activityType = type!!
            data.activity = Activity()
            data.activity!!.activityType = data.activityType
            data.tickets = listOf(ActivityTicket())
            data.attendInfos = listOf(
                    ActivityAttendInfo(title = "姓名", type = "text", required = true),
                    ActivityAttendInfo(title = "手机", type = "text", required = true)
            )
            data.canAttend = true
        }

        model.addAttribute("typeName", if (data.activityType == 2) "赛事" else "活动")
        var titleName = when {
            data.activityType == 1 -> "一般活动"
            data.activityType == 2 -> "体育赛事"
            data.activityType == 3 -> "抽签活动"
            data.activityType == 4 -> "打卡活动"
            else -> null
        }

        model.addAttribute("titleName", titleName)
        model.addAttribute("data", data)

        //重新获取当前团体组织的信息
        var community = communityService!!.getCommunity(this.sessionCommunity.id)
        model.addAttribute("community", community)

        return "sec/community/thirdactivity/publish"
    }

    /**
     * 第三方最新活动
     * 保存活动信息
     */
    @RequestMapping("/publish", method = [RequestMethod.POST])
    @Transactional
    fun postPublish(@RequestBody formData: MultiValueMap<String, String>, model: Model
                    , redirectAttributes: RedirectAttributes): String {
        var jsonData = formData["json_data"]!![0]

        val mapper = jacksonObjectMapper()
        var data = mapper.readValue(jsonData, ThirdActivityPublishData::class.java)

        //设置活动为公开状态
        data.activity!!.public = true
        //设置活动归属组织团体
        data.activity!!.communityId = this.sessionCommunity.id

        if (data.canAttend) {
            data.activity!!.attendDueTime = data.activity!!.endTime
        }
        data.activity!!.attendInfos = mapper.writeValueAsString(data.attendInfos)
        data.activity!!.scoreInfos = mapper.writeValueAsString(data.scoreInfos)

        var user = this.sessionUser
        if (data.activity!!.id == null || data.activity!!.id == 0) {
            //添加活动
            data.activity!!.created = DateUtil.date().toTimestamp()
            data.activity!!.createdBy = user!!.id
            data.activity!!.modified = DateUtil.date().toTimestamp()
            data.activity!!.modifiedBy = user!!.id
            thirdActivityService!!.insert(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            //添加活动票种
            thirdActivityService!!.insertActivityTickets(data.tickets!!.toList())
        } else {
            //更新活动

            data.activity!!.modified = DateUtil.date().toTimestamp()
            data.activity!!.modifiedBy = user!!.id
            thirdActivityService!!.update(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            //删除活动票种
            thirdActivityService!!.deleteActivityTickets(data.activity!!.id)
            //重新添加票种
            thirdActivityService!!.insertActivityTickets(data.tickets!!.toList())
        }

        if (data.activity!!.status == 1) {
            return "redirect:/sec/community/thirdactivity/result?success&type=${data.activity!!.activityType}&id=${data.activity!!.id}"
        }
        else  {
            redirectAttributes.addFlashAttribute("globalMessage", "活动已保存成功！")
            return "redirect:/sec/community/thirdactivity/publish?id=${data.activity!!.id}"
        }
    }

//    /**
//     * 第三方最新活动
//     * 暂存活动信息
//     */
//    @RequestMapping("/save")
//    @ResponseBody
//    @Transactional
//    fun save(@RequestBody formData: MultiValueMap<String, String>, model: Model): Any {
//        var jsonData = formData["json_data"]!![0]
//
//        val mapper = jacksonObjectMapper()
//        var data = mapper.readValue(jsonData, ThirdActivityPublishData::class.java)
//
//        //设置活动为草稿状态
//        data.activity!!.status = 0
//        //设置活动为公开状态
//        data.activity!!.public = true
//        //设置活动归属组织团体
//        data.activity!!.communityId = this.sessionCommunity.id
//
//        if (data.canAttend) {
//            data.activity!!.attendDueTime = data.activity!!.endTime
//        }
//        data.activity!!.attendInfos = mapper.writeValueAsString(data.attendInfos)
//        data.activity!!.scoreInfos = mapper.writeValueAsString(data.scoreInfos)
//
//        var user = this.sessionUser
//        if (data.activity!!.id == null || data.activity!!.id == 0) {
//            //添加活动
//            data.activity!!.created = DateUtil.date().toTimestamp()
//            data.activity!!.createdBy = user!!.id
//            data.activity!!.modified = DateUtil.date().toTimestamp()
//            data.activity!!.modifiedBy = user!!.id
//            thirdActivityService!!.insert(data.activity)
//            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
//            //添加活动票种
//            thirdActivityService!!.insertActivityTickets(data.tickets!!.toList())
//        } else {
//            //更新活动
//
//            data.activity!!.modified = DateUtil.date().toTimestamp()
//            data.activity!!.modifiedBy = user!!.id
//            thirdActivityService!!.update(data.activity)
//            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
//            //删除活动票种
//            thirdActivityService!!.deleteActivityTickets(data.activity!!.id)
//            //重新添加票种
//            thirdActivityService!!.insertActivityTickets(data.tickets!!.toList())
//        }
//
//        return true
//    }

    /**
     * 跳转到编辑活动发布成功页面
     */
    @GetMapping("/result")
    fun result(): String {
        return "sec/community/thirdactivity/result"
    }

    /**
     * 第三方最新活动签到二维码
     * @param id: 活动ID
     * @param model: 模型数据
     * @return 活动签到二维码视图页面
     */
    @CrossOrigin
    @RequestMapping(value = "/qrcode", method = [RequestMethod.POST, RequestMethod.GET])
    fun qrCode(@RequestParam id: Int, model: Model): String {
        val activity = thirdActivityService!!.getActivityAndOthers(id)
        model.addAttribute("activity", activity)

        return "sec/community/thirdactivity/qrcode"
    }

    /**
     * 第三方最新活动
     * 报名签到信息
     * @param request: 请求参数
     * @param model: 模型数据
     * @return 报名用户列表视图页面
     */
    @CrossOrigin
    @RequestMapping(value = "/attendusers", method = [RequestMethod.POST, RequestMethod.GET])
    fun attendUsers(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        //参数：手机号
        var activityId = request.getParameter("activityId")
        var title = request.getParameter("title")
        var mobile = request.getParameter("mobile")
        var real_name = request.getParameter("real_name")
        var ticket_title = request.getParameter("ticket_title")
        var checked = request.getParameter("checked")
        var status = request.getParameter("status")
        var other_info = request.getParameter("other_info")

        //取得活动报名信息
        val items = thirdActivityService!!.getAttendUsers(this.sessionCommunity.id, start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            if (map["other_info"] != null) {
                var otherInfo = mapper.readValue<Map<String, String>>(map["other_info"].toString())
                for (info in otherInfo) {
                    var key = "报名：" + info.key
                    if (!attendColumns.contains(key)) {
                        attendColumns.add(key)
                    }
                    map[key] = info.value
                }
            }
            listItems.add(map)
        }

        //取得统计统计信息
        val activityStatistics = thirdActivityService!!.getActivityStatisticsByTicket(activityId)
        //取得活动报名人数
        var attendCount = thirdActivityService!!.getActivityAttendCount(activityId)
        //取得活动签到人数
        var checkCount = thirdActivityService!!.getActivityCheckCount(activityId)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        //将活动报名信息添加至数据模型
        model.addAttribute("attendColumns", attendColumns)
        model.addAttribute("items", listItems)

        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendCount", attendCount)
        model.addAttribute("checkCount", checkCount)

        return "sec/community/thirdactivity/attendusers"
    }

    /**
     * 第三方最新活动报名签到信息
     * 之取得用户信息集合
     * @param map: 请求参数
     * @return 活动报名用户列表信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getAttendUsers", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getAttendUsers(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var mobile = map["mobile"]
        var real_name = map["real_name"]
        var ticket_title = map["ticket_title"]
        var checked = map["checked"]
        var status = map["status"]
        var other_info = map["other_info"]

        //取得活动报名信息
        val items = thirdActivityService!!.getAttendUsers(this.sessionCommunity.id, start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            if (map["other_info"] != null) {
                var otherInfo = mapper.readValue<Map<String, String>>(map["other_info"].toString())
                for (info in otherInfo) {
                    var key = "报名：" + info.key
                    if (!attendColumns.contains(key)) {
                        attendColumns.add(key)
                    }
                    map[key] = info.value
                }
            }
            listItems.add(map)
        }

        return listItems
    }

    /**
     * 中签处理
     * @param ids: 报名ID数组
     * @return 报名记录集合信息
     */
    @RequestMapping(value = "/approve", method = [RequestMethod.POST])
    @ResponseBody
    fun approve(@RequestBody ids: Array<Int>): List<ActivityUser> {
        var list = mutableListOf<ActivityUser>()
        for (id in ids) {
            var activityUser=thirdActivityService!!.getActivityUser(id)
            var activity=thirdActivityService!!.getActivity(activityUser!!.activityId)
            if(activity!!.activityType==3){
                //更新报名状态值
                thirdActivityService!!.updateActivityUserStatus(id, 2)
                list.add(thirdActivityService!!.getActivityUser(id))
            }
        }

        return list
    }

    /**
     * 退款处理
     * @param ids: 报名ID数组
     * @return 处理成功记录数
     */
    @RequestMapping(value = "/refund", method = [RequestMethod.POST])
    @ResponseBody
    fun refund(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerRefund(it) }
    }

    /**
     * 检查退款是否成功
     * @param ids: 报名ID数组
     * @return 处理成功记录数
     */
    @RequestMapping(value = "/checkrefund", method = [RequestMethod.POST])
    @ResponseBody
    fun checkRefund(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerCheckRefund(it) }
    }

    /**
     * 单个活动报名记录退款处理
     * @param id: 报名ID
     * @return true/false
     */
    @Transactional
    fun innerRefund(id: Int): Boolean {
        var start = DateUtil.date()
        var order = thirdActivityService!!.getActivityUserOrder(id)
        //更新报名状态值
        if (order != null) {
            var refundOutTradeNo = "D${start.toString("yyyyMMddHHmmss")}" + String.format("%08d", order.id)

            var refundRequest = WxPayRefundRequest()
            refundRequest.outTradeNo = order.extenalId
            refundRequest.outRefundNo = refundOutTradeNo

            var price = (order.price.toDouble() * 100).toInt()
            refundRequest.totalFee = price
            refundRequest.refundFee = price

            var result = wxService!!.refund(refundRequest)
            if (result.resultCode == "SUCCESS") {
                thirdActivityService!!.updateActivityUserStatus(id, 3)

                order.refundTradeNo = refundOutTradeNo
                order.refundTime = start.toTimestamp()
                //退款状态->已申请退款
                order.refundStatus = 1

                thirdActivityService!!.updateActivityUserOrder(order)
                return true
            }
        }
        return false
    }

    /**
     * 单个活动报名记录检查退款处理
     * @param id: 报名ID
     * @return true/false
     */
    @Transactional
    fun innerCheckRefund(id: Int): Boolean {

        var order = thirdActivityService!!.getActivityUserOrder(id, 2, 1)
        if (order != null) {
            var refundOutTradeNo = order.refundTradeNo
            var result = wxService!!.payService!!.refundQuery(null, null, refundOutTradeNo, null)
            if (result.resultCode == "SUCCESS") {
                thirdActivityService!!.updateActivityUserStatus(id, 4)
                //退款状态->已完成退款
                order.refundStatus = 2
                thirdActivityService!!.updateActivityUserOrder(order)

                return true
            }
        }
        return false
    }

    /**
     * 检查是否已付款成功
     * @param ids: 报名ID数组
     * @return 处理成功记录数
     */
    @RequestMapping(value = "/checkOrder", method = [RequestMethod.POST])
    @ResponseBody
    fun checkOrder(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerCheckOrder(it) }
    }

    /**
     * 查询微信订单
     * @param id: 订单ID
     */
    @Transactional
    fun innerCheckOrder(id: Int): Boolean {

        //查询未支付的订单
        var order = thirdActivityService!!.getPayOrder(id, 1)
        if (order != null) {
            var tradeNo = order.extenalId
            var otherInfo = order.otherInfo
            var activityId = order.activityId

            LogUtil.printLog("验证订单：$tradeNo 活动编号: $activityId 报名信息: $otherInfo")
            var rs: WxPayOrderQueryResult?
            try {
                rs = wxService!!.payService!!.queryOrder(null, tradeNo)
            }
            catch (e: WxPayException)
            {
                LogUtil.printLog("查询订单异常: $e")
                return false
            }

            val tradeState = rs!!.tradeState
            LogUtil.printLog("$tradeNo tradeState: $tradeState")
            //如果用户订单交易状态成功
            if (tradeState == "SUCCESS") {

                if (activityId != null) {

                    //活动缴费
                    val mapper = jacksonObjectMapper()
                    var map = mapper.readValue<Map<String, String>>(otherInfo)
                    var count = thirdActivityService!!.getActivityUserCount(order.activityId, order.userId)
                    if (count == 0) {
                        //补充用户报名记录
                        var activityUser = ActivityUser()
                        activityUser.userId = order.userId
                        activityUser.activityId = order.activityId
                        activityUser.activityTicketId = order.activityTicketId
                        activityUser.created = order.created
                        activityUser.createdBy = order.userId
                        activityUser.attendTime = order.created
                        activityUser.realName = map["realName"]
                        activityUser.mobile = map["mobile"]
                        activityUser.otherInfo = map["otherInfo"]
                        activityUser.price =  map["price"] as BigDecimal
                        activityUser.score = map["score"] as Int
                        thirdActivityService!!.insertActivityUser(activityUser)
                        LogUtil.printLog("补充用户报名记录：$otherInfo")


                        //更新订单付款状态和付款时间
                        order.status = 2
                        order.payTime = order.created
                        thirdActivityService!!.updateActivityUserOrder(order)
                        LogUtil.printLog("更新订单付款状态->2：$tradeNo")
                        return true
                    } else {
                        LogUtil.printLog("用户报名记录已存在：$otherInfo")
                    }
                }
                else {

                    //升级成员马协会员
                    var user = userService!!.getUser(order.userId)
                    user.level = otherInfo.toInt()
                    userService!!.update(user)
                    LogUtil.printLog("更新用户会员年度->$otherInfo: $tradeNo")

                    order.status = 2
                    order.payTime = order.created
                    thirdActivityService!!.updateActivityUserOrder(order)
                    LogUtil.printLog("更新订单付款状态->2: $tradeNo")
                }
            }
        }
        return false
    }

    /**
     * 取消中签处理
     * @param ids: 报名ID数组
     * @return 报名列表信息
     */
    @RequestMapping(value = "/cancel", method = [RequestMethod.POST])
    @ResponseBody
    fun cancel(@RequestBody ids: Array<Int>): List<ActivityUser> {
        var list = mutableListOf<ActivityUser>()
        for (id in ids) {
            var activityUser=thirdActivityService!!.getActivityUser(id)
            var activity=thirdActivityService!!.getActivity(activityUser!!.activityId)
            if(activity!!.activityType==3){
                //更新报名状态值
                thirdActivityService!!.updateActivityUserStatus(id, 1)
                list.add(thirdActivityService!!.getActivityUser(id))
            }
        }

        return list
    }

    /**
     * 删除报名记录
     * @param ids: 报名ID数组
     * @return 成功处理记录数
     */
    @RequestMapping(value = "/deleteAttendUsers", method = [RequestMethod.POST])
    @ResponseBody
    fun deleteAttendUsers(@RequestBody ids: Array<Int>): Int {
        return ids.count { innerDelete(it) }
    }

    /**
     * 删除报名记录
     * @param id: 报名ID
     */
    @Transactional
    fun innerDelete(id: Int): Boolean {

        //删除报名记录时，检查是否有缴费订单
        var order = thirdActivityService!!.getActivityUserOrder(id)
        if (order == null) {
            //直接删除报名记录
            thirdActivityService!!.deleteActivityUser(id)
            return true
        }
        else {
            if (order!!.refundStatus == 1) {
                //已申请退款，但是未完成退款，不可删除
                LogUtil.printLog("报名记录已申请退款，但是未完成退款，不可删除！报名ID: $id")
                return false
            }
            else if (order!!.refundStatus == 2) {
                //已完成退款
                //直接删除报名记录
                thirdActivityService!!.deleteActivityUser(id)
                return true
            }
            else {
                if (this.innerRefund(id) && this.innerCheckRefund(id)) {
                    //先退款、后删除报名记录
                    thirdActivityService!!.deleteActivityUser(id)
                    return true
                }
            }
        }

        return false
    }

    /**
     * 取得活动信息集合
     */
    private val getActivities: List<Activity>
        @RequestMapping(value = "/getActivities")
        @ResponseBody
        get() {
            return thirdActivityService!!.getAllActivities()
        }


    /**
     * 第三方活动管理之
     * 删除活动信息
     * @param id: 活动ID
     * @return 活动列表视图页面
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteActivity/{id}", method = [RequestMethod.GET, RequestMethod.POST])
    fun deleteActivity(@PathVariable id: Int, model: Model): String {
        thirdActivityService!!.deleteById(id)

        return "redirect:/sec/community/thirdactivity/activities"
    }

    /**
     * 第三方活动
     * 之报名信息
     * @param activityId: 活动ID
     * @param model: 模型数据
     * @return 活动报名二维码视图页面
     */
    @CrossOrigin
    @RequestMapping(value = "/attend/{activityId}")
    fun attend(@PathVariable activityId: Int, model: Model): String {
        var user = this.sessionUser
        model.addAttribute("user", user)

        var activity = thirdActivityService!!.getActivityAndOthers(activityId)
        model.addAttribute("activity", activity)

        return "sec/community/thirdactivity/attend"
    }

    /**
     * 第三方打卡活动统计
     */
    @CrossOrigin
    @RequestMapping(value = "/checkInData", method = [RequestMethod.POST, RequestMethod.GET])
    fun checkInData(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)

        return "sec/community/thirdactivity/checkInData"
    }

    /**
     *
     * 第三方打卡活动统计
     * @param map: 查询条件参数
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getCheckInData", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getCheckInData(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var username = map["username"]
        var displayname = map["displayname"]

        return thirdActivityService!!.getCheckInData(start, end, activityId, title, username, displayname, this.sessionCommunity.id).intoMaps()
    }

    /**
     * 第三方打卡明细查询
     */
    @CrossOrigin
    @RequestMapping(value = "/checkInList", method = [RequestMethod.POST, RequestMethod.GET])
    fun checkInList(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)

        return "sec/community/thirdactivity/checkInList"
    }

    /**
     *
     * 第三方打卡活动明细
     * @param map: 查询条件参数
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getCheckInList", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getCheckInList(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var username = map["username"]
        var displayname = map["displayname"]

        return thirdActivityService!!.getCheckInList(start, end, activityId, title, username, displayname, this.sessionCommunity.id).intoMaps()
    }
}