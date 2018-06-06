package com.kylin.activity.controller.sec

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityTicketDao
import com.kylin.activity.databases.tables.daos.ActivityUserDao
import com.kylin.activity.databases.tables.daos.PayOrderDao
import com.kylin.activity.databases.tables.pojos.*
import com.kylin.activity.model.ActivityAttendInfo
import com.kylin.activity.model.ActivityScoreInfo
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.UserService
import com.kylin.activity.service.WxService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

data class ActivityPublishData(
        var activity: Activity? = null,
        var tickets: List<ActivityTicket>? = null,
        var attendInfos: List<ActivityAttendInfo>? = null,
        var canAttend: Boolean = false,
        var activityType: Int? = 0,
        var scoreInfos: ActivityScoreInfo? = null
)

@Controller
@RequestMapping("sec/activity")
class ActivityController : BaseController() {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    @Autowired
    private val activityUserDao: ActivityUserDao? = null

    @Autowired
    private val payOrderDao: PayOrderDao? = null

    @Autowired
    private val wxService: WxService? = null
    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    @Autowired
    private val create: DSLContext? = null

    @GetMapping("/publish")
    fun getPublish(@RequestParam(required = false) id: Int?, @RequestParam(required = false) type: Int?, model: Model): String {

        //检查用户权限
        if (!userService!!.checkPermission("PUBLISH")) {
            return "pub/error/20"
        }

        var data = ActivityPublishData()
        if (id != null && id > 0) {
            data.activity = activityDao!!.fetchOneById(id)
            data.tickets = activityTicketDao!!.fetchByActivityId(id)
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
        model.addAttribute("data", data)
        return "sec/activity/publish"
    }

    @PostMapping("/publish")
    @Transactional
    fun postPublish(@RequestBody formData: MultiValueMap<String, String>, model: Model): String {
        var jsonData = formData["json_data"]!![0]

        val mapper = jacksonObjectMapper()
        var data = mapper.readValue(jsonData, ActivityPublishData::class.java)

        //设置活动为公开状态
        data.activity!!.public = true

        if (data.canAttend) {
            data.activity!!.attendDueTime = data.activity!!.endTime
        }
        data.activity!!.attendInfos = mapper.writeValueAsString(data.attendInfos)
        data.activity!!.scoreInfos = mapper.writeValueAsString(data.scoreInfos)

        var user = userService!!.getCurrentUserInfo()
        data.activity!!.created = DateUtil.date().toTimestamp()
        data.activity!!.createdBy = user!!.id

        if (data.activity!!.id == null || data.activity!!.id == 0) {
            activityDao!!.insert(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            activityTicketDao!!.insert(data.tickets!!.toList())
        } else {
            activityDao!!.update(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            create!!.deleteFrom(Tables.ACTIVITY_TICKET)
                    .where(Tables.ACTIVITY_TICKET.ACTIVITY_ID.eq(data.activity!!.id))
                    .execute()
            activityTicketDao!!.insert(data.tickets!!.toList())
        }

        return "redirect:/sec/activity/result?success&type=${data.activity!!.activityType}&id=${data.activity!!.id}"
    }

    @GetMapping("/result")
    fun result(model: Model): String {
        return "sec/activity/result"
    }

    /**
     * 报名
     */
    @GetMapping("/attend/{activityId}")
    fun attend(@PathVariable activityId: Int,
               request: HttpServletRequest,
               model: Model): String {
        //从session中当前用户信息
        var user = this.sessionUser
        model.addAttribute("user", user)

//        if (user!!.isReal == null || !user!!.isReal) {
//            //设置当前请求路径至Request中，转发处理后重新定向回来
//            request.setAttribute("current_url", request.requestURI)
//            //如果用户不是会员，则需要注册成为会员
//            return "forward:/sec/user/registermember"
//        }

        //取得活动详情信息
        var activity = activityService!!.getActivityAndOthers(activityId)
        model.addAttribute("activity", activity)
        return "sec/activity/attend"
    }

    /**
     * 活动
     * @param model
     * *
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/activities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activities(request: HttpServletRequest, model: Model): String {
        /*//标题
         var title = request.getParameter("title")
         //活动分类
         var tags = request.getParameter("tags")
         //时间状态
         var status = request.getParameter("status"
                 var activityItems = activityService !!. getAllActivityUserItems (title, tags, status)
         model.addAttribute("activities", activityItems)
        */
        return "sec/activity/activities"
    }

    /**
     * 取得活动信息集合
     */
    @CrossOrigin
    @RequestMapping(value = "/getActivities", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun activities(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var status = map["status"]
        var tags = map["tags"]
        var title = map["title"]

        //团体名称
        var name = map["name"]
        var items = activityService!!.getAllActivityUserItems(title, tags, status, name)
        var list = items.intoMaps()
        return list
    }

    /**
     * 报名信息
     * @param request
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/attendusers", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun attendusers(request: HttpServletRequest, model: Model): String {
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
        val items = activityService!!.getAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }

        //取得统计统计信息
        val activityStatistics = activityService!!.getActivityStatisticsByTicket(activityId)
        //取得活动报名人数
        var attendcount = activityService!!.getActivityAttendCount(activityId)
        //取得活动签到人数
        var checkcount = activityService!!.getActivityCheckCount(activityId)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        //将活动报名信息添加至数据模型
        model.addAttribute("attendColumns", attendColumns)
        model.addAttribute("items", listItems)

        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendcount", attendcount)
        model.addAttribute("checkcount", checkcount)
        return "sec/activity/attendusers"
    }

    /**
     * 取得用户信息集合
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getAttendUsers", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun attendusers(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        val start = map["start"]
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
        val items = activityService!!.getAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }

        return listItems
    }

    /**
     * 阳光杯活动报名记录
     */
    @CrossOrigin
    @RequestMapping(value = "/sunnycupattendusers", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun sunnycupattendusers(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //2018-03-01
            calendar = GregorianCalendar()
            calendar.set(2018, 2, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        var activityId = request.getParameter("activityId")
        var title = request.getParameter("title")
        var mobile = request.getParameter("mobile")
        var real_name = request.getParameter("real_name")
        var ticket_title = request.getParameter("ticket_title")
        var checked = request.getParameter("checked")
        var status = request.getParameter("status")
        var other_info = request.getParameter("other_info")

        //取得活动报名信息
        val items = activityService!!.getSunnyCupAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }
        //取得统计统计信息
        val activityStatistics = activityService!!.getActivityStatisticsByTicket(activityId)
        //取得活动报名人数
        var attendcount = activityService!!.getActivityAttendCount(activityId)
        //取得活动签到人数
        var checkcount = activityService!!.getActivityCheckCount(activityId)


        model.addAttribute("start", start)
        model.addAttribute("end", end)
        //将活动报名信息添加至数据模型
        model.addAttribute("attendColumns", attendColumns)
        model.addAttribute("items", listItems)
        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendcount", attendcount)
        model.addAttribute("checkcount", checkcount)
        return "sec/activity/sunnycupattendusers"
    }


    /**
     * 阳光杯活动报名记录
     */
    @CrossOrigin
    @RequestMapping(value = "/sunnycup", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun getSunnyCup(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //2018-03-01
            calendar = GregorianCalendar()
            calendar.set(2018, 2, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        var activityId = request.getParameter("activityId")
        var title = request.getParameter("title")
        var mobile = request.getParameter("mobile")
        var real_name = request.getParameter("real_name")
        var ticket_title = request.getParameter("ticket_title")
        var checked = request.getParameter("checked")
        var status = request.getParameter("status")
        var other_info = request.getParameter("other_info")

        //取得活动报名信息
        var items = activityService!!.getSunnyCupAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }
            break
        }

        //取得统计统计信息
        var activityStatistics = activityService!!.getActivityStatisticsByTicket(activityId)
        //取得活动报名人数
        var attendcount = activityService!!.getActivityAttendCount(activityId)
        //取得活动签到人数
        var checkcount = activityService!!.getActivityCheckCount(activityId)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        model.addAttribute("attendColumns", attendColumns)
        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendcount", attendcount)
        model.addAttribute("checkcount", checkcount)

        return "sec/activity/sunnycup"
    }

    /**
     * 取得用户信息集合
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getSunnyCup", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun getSunnyCup(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        val start = map["start"]
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
        val items = activityService!!.getSunnyCupAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }

        return listItems
    }

    /**
     * 中签处理
     */
    @RequestMapping(value = "/approve", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun approveAttendUser(@RequestBody ids: Array<Int>): List<ActivityUser> {
        var list = mutableListOf<ActivityUser>()
        for (id in ids) {
            //System.out.println(id)
            //更新报名状态值
            activityService!!.updateActivityUserStatus(id, 2)
            list.add(activityUserDao!!.fetchOneById(id))
        }

        return list
    }

    /**
     * 退款处理
     */
    @RequestMapping(value = "/refund", method = arrayOf(RequestMethod.POST))
    @ResponseBody

    fun refundAttendUser(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerRefund(it) }
    }

    /**
     * 检查退款是否成功
     */
    @RequestMapping(value = "/checkrefund", method = arrayOf(RequestMethod.POST))
    @ResponseBody

    fun checkRrefundAttendUser(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerCheckRefund(it) }
    }

    @Transactional
    fun innerRefund(id: Int): Boolean {
        var start = DateUtil.date()


        var order = create!!.fetchOne("select t2.* from activity_user t1 inner join pay_order t2 on t1.user_id = t2.user_id and t1.activity_id = t2.activity_id and t1.activity_ticket_id = t2.activity_ticket_id\n" +
                "and t2.status = 2 and t1.id=? order by created desc  limit 1", id)
        //System.out.println(id)
        //更新报名状态值
        if (order != null) {
            var refundOutTradeNo = "D${start.toString("yyyyMMddHHmmss")}" + String.format("%08d", order["id"])

            var refundRequest = WxPayRefundRequest()
            refundRequest.outTradeNo = order["extenal_id"].toString()
            refundRequest.outRefundNo = refundOutTradeNo

            var price = ((order.get("price", Double::class.java)) * 100).toInt()
            refundRequest.totalFee = price
            refundRequest.refundFee = price


            var result = wxService!!.refund(refundRequest)
            if (result.resultCode == "SUCCESS") {
                activityService!!.updateActivityUserStatus(id, 3)
                create!!.execute("update pay_order set refund_trade_no=? , refund_time = ?, refund_status = ? where id = ?",
                        refundOutTradeNo, start.toTimestamp(), 1, order["id"])
                return true
            }
        }
        return false
    }

    @Transactional
    fun innerCheckRefund(id: Int): Boolean {

        var item = create!!.fetchOne("select t2.* from activity_user t1 inner join pay_order t2 on t1.user_id = t2.user_id and t1.activity_id = t2.activity_id and t1.activity_ticket_id = t2.activity_ticket_id\n" +
                "and t2.status = 2 and t2.refund_status=1 and t1.id=? order by created desc  limit 1", id)

        if (item != null) {

            var order = item

            var refundOutTradeNo = order["refund_trade_no"].toString()

            var tradeNo = order["extenal_id"].toString()

            var result = wxService!!.payService!!.refundQuery(null, null, refundOutTradeNo, null)
            if (result.resultCode == "SUCCESS") {
                create!!.execute("update pay_order set refund_status = ? where id = ?",
                        2, order["id"])
                activityService!!.updateActivityUserStatus(id, 4)
                return true
            }
        }
        return false
    }

    /**
     * 取消中签处理
     */
    @RequestMapping(value = "/cancel", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun cancelAttendUser(@RequestBody ids: Array<Int>): List<ActivityUser> {
        var list = mutableListOf<ActivityUser>()
        for (id in ids) {
            //System.out.println(id)
            //更新报名状态值
            activityService!!.updateActivityUserStatus(id, null)
            list.add(activityUserDao!!.fetchOneById(id))
        }

        return list
    }


    /**
     * 删除报名记录
     */
    @RequestMapping(value = "/deleteAttendUsers", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun deleteAttendUsers(@RequestBody ids: Array<Int>): Int {
        return ids.count { innerDelete(it) }
    }

    @Transactional
    fun innerDelete(id: Int): Boolean {
        var start = DateUtil.date()
        var order = create!!.fetchOne("select t2.* from activity_user t1 inner join pay_order t2 on t1.user_id = t2.user_id and t1.activity_id = t2.activity_id and t1.activity_ticket_id = t2.activity_ticket_id\n" +
                "and t2.status = 2 and t1.id=? order by created desc  limit 1", id)
        //如果有对应的报名订单，则先退费处理
        if (order != null) {
            var refundOutTradeNo = "D${start.toString("yyyyMMddHHmmss")}" + String.format("%08d", order["id"])
            var refundRequest = WxPayRefundRequest()
            refundRequest.outTradeNo = order["extenal_id"].toString()
            refundRequest.outRefundNo = refundOutTradeNo
            var price = ((order.get("price", Double::class.java)) * 100).toInt()
            refundRequest.totalFee = price
            refundRequest.refundFee = price
            var result = wxService!!.refund(refundRequest)
            if (result.resultCode == "SUCCESS") {
                activityService!!.updateActivityUserStatus(id, 3)
                create!!.execute("update pay_order set refund_trade_no=? , refund_time = ?, refund_status = ? where id = ?",
                        refundOutTradeNo, start.toTimestamp(), 1, order["id"])
            }
        }

        //删除报名记录
        activityUserDao!!.deleteById(id)
        return true
    }

    /**
     * 取得活动信息集合
     */
    private val getActivities: List<Activity>
        @RequestMapping(value = "/getActivities")
        @ResponseBody
        get() {
            val items = activityService!!.getAllActivities()
            return items
        }

    /**
     * 删除活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteActivity/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteActivity(@PathVariable id: Int, model: Model): String {
        activityService!!.deleteById(id)

        return "redirect:/sec/activity/activities"
    }

    /**
     * 收藏活动，返回活动收藏数
     */
    @RequestMapping(value = "/favorite", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun favorite(@RequestParam id: Int): Int {
        var activityFavorite = ActivityFavorite()
        activityFavorite.activityId = id
        activityFavorite.userId = this.sessionUser.id
        //创建活动收藏
        activityService!!.createActivityFavorite(activityFavorite)

        return activityService!!.getActivityFavoriteCount(id)
    }

    /**
     * 活动签到二维码
     * @param model
     * *
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/qrcode", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun qrCode(@RequestParam id: Int, model: Model): String {
        val activity = activityService!!.getActivityAndOthers(id)

        model.addAttribute("activity", activity)
        return "sec/activity/qrcode"
    }
}