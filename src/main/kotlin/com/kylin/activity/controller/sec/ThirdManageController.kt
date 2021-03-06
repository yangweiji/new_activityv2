package com.kylin.activity.controller.sec

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ScoreHistory
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 第三方团体组织团体中心控制器
 * @author Richard C. Hu
 */
@Controller
@RequestMapping("sec/community/thirdmanage")
class ThirdManageController : BaseController() {
    /**
     * 订单服务
     */
    @Autowired
    private val oderService: ThirdOrderService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 团体组织活动服务
     */
    @Autowired
    private val thirdActivityService: ThirdActivityService? = null

    /**
     * 微信接口服务
     */
    @Autowired
    private val wxService: WxService? = null

    /**
     * 团体组织积分服务
     */
    @Autowired
    private val scoreService: ThirdScoreService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null


    /**
     * 第三方管理中心，最新活动查询页面
     * @param status: 活动状态
     * @param model: 模型
     * @return 最新活动页面
     */
    @CrossOrigin
    @RequestMapping(value = "/home/{status}", method = [RequestMethod.GET])
    private fun home(@PathVariable status: Int?, model: Model): String {
        var user = this.sessionUser
        model.addAttribute("user", user)

        //取得最新的活动记录
        var activityItems = thirdActivityService!!.getAllActivityUserItemsAndCommunity(status!!, this.sessionCommunity.id)
        for (r in activityItems) {
            //更新活动图片的URL
            if (r.get("user_avatar") != null) {
                r.setValue(r.fieldsRow().field("user_avatar", String::class.java), commonService!!.getDownloadUrl(r.get("user_avatar").toString()))
            }
        }

        //参数存储到数据模型
        model.addAttribute("status", status)
        //活动记录存储到数据模型
        model.addAttribute("activities", activityItems)

        return "sec/community/thirdmanage/home"
    }

    /**
     *  第三方查询用户积分
     *  @param score: 积分信息
     *  @param request: 请求参数
     *  @param model: 模型
     *  @return 积分管理页面
     */
    @CrossOrigin
    @RequestMapping(value = "/scores", method = [RequestMethod.POST, RequestMethod.GET])
    fun scores(@ModelAttribute score: ScoreHistory, request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为上个月第一日
            calendar.add(Calendar.MONTH, -1)
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
        model.addAttribute("score", score)

        return "sec/community/thirdmanage/scores"
    }

    /**
     * 异步查询用户积分
     * @param map: 查询条件
     * @return 用户积分列表信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getScores", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getScores(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var username = map["username"]
        var real_name = map["real_name"]

        //取得活动积分明细
        var items = scoreService!!.getUserActivityScores(start, end, activityId, title, username, real_name, this.sessionCommunity.id)
        return items.intoMaps()
    }

    /**
     *  第三方删除用户积分
     *  @param id: 积分ID
     *  @return 积分管理页面
     */
    @RequestMapping(value = "/deleteActivityScore", method = [RequestMethod.POST])
    @ResponseBody
    fun deleteActivityScore(@RequestParam(required = true) id: Int?): Any {
        scoreService!!.deleteById(id)

        return true
    }

    /**
     * 第三方编辑用户积分
     * @param id: 积分ID
     * @param model: 模型
     * @return 积分编辑页面
     */
    @RequestMapping(value = "/editActivityScore/{id}", method = [RequestMethod.GET])
    private fun editActivityScore(@PathVariable id: Int?
                                  , model: Model): String {
        var score = scoreService!!.getScoreHistory(id)
        var user = userService!!.getUser(score.userId!!)
        model.addAttribute("score", score)
        model.addAttribute("user", user)

        return "sec/community/thirdmanage/score"
    }

    /**
     * 第三方添加用户积分
     * @param user: 用户信息
     * @param score: 积分信息
     * @param model: 模型
     * @return 积分编辑页面
     */
    @RequestMapping(value = "/createActivityScore", method = [RequestMethod.GET])
    @Throws(Exception::class)
    private fun createActivityScore(model: Model): String {
        var user = User()
        var score = ScoreHistory()
        model.addAttribute("user", user)
        model.addAttribute("score", score)

        return "sec/community/thirdmanage/score"
    }

    /**
     * 保存修改及添加用户积分
     * 验证用户账号及编号是否存在
     * @param user: 用户信息
     * @param score: 积分信息
     * @param redirectAttributes: 重定向属性
     * @param model: 模型
     * @return 积分管理页面
     */
    @RequestMapping(value = "/saveActivityScore", method = [RequestMethod.POST])
    @Throws(Exception::class)
    private fun saveActivityScore(@ModelAttribute("score") score: ScoreHistory
                                  , @ModelAttribute("user") user: User?
                                  , redirectAttributes: RedirectAttributes
                                  , model: Model): String {
        //取得用户信息
        var u = userService!!.getUser(user!!.username)

        //验证用户账号
        if (u == null) {
            model.addAttribute("errorMessage", "用户账号: ${user!!.username} 无效！")
            return "sec/community/thirdmanage/score"
        }

        //验证活动编号
        if (activityService!!.getCommunityActivity(score.activityId!!, this.sessionCommunity.id) == null) {
            model.addAttribute("errorMessage", "活动编号: ${score.activityId} 无效！")
            return "sec/community/thirdmanage/score"
        }

        if (score.id == null || score.id == 0) {
            var existScore = scoreService!!.getScoreHistories(u.id, score.activityId, this.sessionCommunity.id)
            if (existScore != null) {
                model.addAttribute("errorMessage", "一个用户在同一个活动中只允许添加一次积分！")
                return "sec/community/thirdmanage/score"
            }
        }

        score.userId = u.id
        score.communityId = this.sessionCommunity.id
        scoreService!!.save(score)
        LogUtil.printLog("保存用户积分成功, 积分ID: ${score.id}")

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")

        return "redirect:/sec/community/thirdmanage/scores"
    }

    /**
     * 用户缴费订单信息
     * @param model: 模型
     * @param request: 请求
     * @return 订单管理页面
     */
    @CrossOrigin
    @RequestMapping(value = "/payments", method = [RequestMethod.GET, RequestMethod.POST])
    private fun payments(model: Model, request: HttpServletRequest): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为上个月第一日
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        var status = request.getParameter("status")
        if (status.isNullOrBlank()) {
            status = "2" //完成付款
        }
        model.addAttribute("start", start)
        model.addAttribute("end", end)
        model.addAttribute("status", status)

        return "sec/community/thirdmanage/payments"
    }

    /**
     * 订单管理异步查找订单信息
     * @param map: 查询条件
     * @return 订单列表信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getPayments", method = [RequestMethod.GET, RequestMethod.POST])
    @ResponseBody
    fun getPayments(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var username = map["username"]
        var real_name = map["real_name"]
        var mobile = map["mobile"]
        var ticket_title = map["ticket_title"]

        var extenal_id = map["extenal_id"]
        var status = map["status"]
        var refund_trade_no = map["refund_trade_no"]
        var refund_status = map["refund_status"]

        //取得订单信息
        var items = oderService!!.getUserActivityPayments(start, end, activityId, title, username, real_name, mobile, ticket_title, extenal_id, status, refund_trade_no, refund_status, this.sessionCommunity.id)
        return items.intoMaps()
    }

    /**
     * 用户订单统计信息
     */
    @CrossOrigin
    @RequestMapping(value = "/ordersstatistics", method = [RequestMethod.GET, RequestMethod.POST])
    private fun ordersstatistics(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为上个月第一日
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        var title = request.getParameter("title")
        //取得用户缴费订单信息
        var items = oderService!!.getOrdersStatistics(title, start, end, this.sessionCommunity.id)
        var totalAmount: Double = 0.0
        for (item in items) {
            totalAmount += java.lang.Double.parseDouble(item.getValue("amount").toString())
            //BigDecimal可以把一个double类型的数值保留两位小数，并且可以实现数值的四舍五入
            totalAmount = BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        model.addAttribute("totalAmount", totalAmount)
        model.addAttribute("items", items)

        return "sec/community/thirdmanage/ordersstatistics"
    }


    /**
     * 申请退款处理
     * @param ids: 报名ID数组
     * @return 处理成功记录数
     */
    @CrossOrigin
    @RequestMapping(value = "/refund", method = [RequestMethod.POST])
    @ResponseBody
    private fun refund(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerRefund(it) }
    }

    /**
     * 检查退款是否成功
     * @param ids: 报名ID数组
     * @return 处理成功记录数
     */
    @CrossOrigin
    @RequestMapping(value = "/checkrefund", method = [RequestMethod.POST])
    @ResponseBody
    private fun checkRefund(@RequestBody ids: Array<Int>): Int {

        return ids.count { innerCheckRefund(it) }
    }

    /**
     * 单个活动报名记录退款处理
     * @param id: 报名ID
     * @return true/false
     */
    @Transactional
    private fun innerRefund(id: Int): Boolean {
        var start = DateUtil.date()
        var order = thirdActivityService!!.getPayOrder(id)

        //已完成付款的订单，尚未申请退款的订单处理
        if (order!!.status == 2 && order!!.refundStatus == null) {
            var refundOutTradeNo = "D${start.toString("yyyyMMddHHmmss")}" + String.format("%08d", order.id)

            var refundRequest = WxPayRefundRequest()
            refundRequest.outTradeNo = order.extenalId
            refundRequest.outRefundNo = refundOutTradeNo

            var price = (order.price.toDouble() * 100).toInt()
            refundRequest.totalFee = price
            refundRequest.refundFee = price

            var result = wxService!!.refund(refundRequest)
            if (result.resultCode == "SUCCESS") {
                //同步更新报名记录的状态
                thirdActivityService!!.updateActivityUserStatus(order.userId, order.activityId, 3)

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
    private fun innerCheckRefund(id: Int): Boolean {

        var order = thirdActivityService!!.getPayOrder(id)
        //已申请退款的订单处理
        if (order!!.status == 2 && order!!.refundStatus == 1) {
            var refundOutTradeNo = order.refundTradeNo
            var result = wxService!!.payService!!.refundQuery(null, null, refundOutTradeNo, null)
            if (result.resultCode == "SUCCESS") {
                //同步更新报名记录的状态
                thirdActivityService!!.updateActivityUserStatus(order.userId, order.activityId, 4)
                //退款状态->已完成退款
                order.refundStatus = 2
                thirdActivityService!!.updateActivityUserOrder(order)

//                if (order!!.activityId == null)
//                {
//                    //已完成退款后，对升级会员用户进行更新
//                    var user = userService!!.getUser(order.userId)
//                    user.level = null
//                    userService!!.update(user)
//                    LogUtil.printLog("取消用户的会员资格, 用户ID: ${order.userId}")
//                }
//                else {
//                    //已完成退款后，对活动报名记录进行删除
//                    thirdActivityService!!.deleteActivityUser(order!!.userId, order!!.activityId, order!!.activityTicketId)
//                    LogUtil.printLog("删除活动报名记录, 用户ID: ${order.userId}, 活动ID: ${order.activityId}, 活动票种ID: ${order.activityTicketId}")
//                }

                return true
            }
        }
        return false
    }

}