package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ScoreHistory
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.OrderService
import com.kylin.activity.service.ScoreService
import com.kylin.activity.service.UserService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest


/**
 * Created by 9kylin on 2017-11-22.
 */
@Controller
@RequestMapping("sec/admin/manage")
class ManageCenterController : BaseController() {

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
     * 积分服务
     */
    @Autowired
    private val scoreService: ScoreService? = null

    /**
     * 订单服务
     */
    @Autowired
    private val oderService: OrderService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 管理中心，最新活动查询页面
     *
     * @param status: 活动状态
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/home/{status}", method = arrayOf(RequestMethod.GET))
    private fun home(@PathVariable status: Int?, model: Model): String {
        var user = this.sessionUser
        model.addAttribute("user", user)
        //取得最新的活动记录
        var activityItems = activityService!!.getAllActivityUserItems(status!!)
        for (r in activityItems) {
            //更新用户头像图片URL
            if (r.get("user_avatar") != null) {
                r.setValue(r.fieldsRow().field("user_avatar", String::class.java), commonService!!.getDownloadUrl(r.get("user_avatar").toString(), "small"))
            }
        }

        //参数存储至数据模型
        model.addAttribute("status", status)
        //活动记录存储只数据模型
        model.addAttribute("activities", activityItems)
        return "sec/manage/home"
    }


    /**
     * 用户活动积分信息
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/scores", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun scores(@ModelAttribute score: ScoreHistory,
                       request: HttpServletRequest,
                       model: Model): String {
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
        model.addAttribute("score", score)

        return "sec/admin/manage/scores"
    }

    /**
     * 查找积分RestController
     * @author Richard
     */
    @CrossOrigin
    @RequestMapping(value = "/getScores", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun scores(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var username = map["username"]
        var real_name = map["real_name"]

        //团体名称
        var communityname = map["communityname"]

        //取得活动积分明细
        var items = scoreService!!.getUserActivityScores(start, end, activityId, title, username, real_name, communityname)
        var list = items.intoMaps()
        return list
    }

    /**
     * 添加用户活动积分
     * @param model
     * @return
     */
    @RequestMapping(value = "/score", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @Throws(Exception::class)
    private fun createActivityScore(
            @ModelAttribute score: ScoreHistory,
            @ModelAttribute user: User,
            model: Model): String {

        model.addAttribute("score", score)
        model.addAttribute("user", user)
        return "sec/manage/score"
    }

    /**
     * 修改活动积分
     * @param id
     * @param score
     * @param model
     * @return
     */
    @RequestMapping(value = "/score/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun editActivityScore(
            @PathVariable id: Int?,
            @ModelAttribute score: ScoreHistory,
            @ModelAttribute user: User,
            model: Model): String {
        var score = score
        var user = user
        score = scoreService!!.getScoreHistory(id)
        user = userService!!.getUser(score.userId!!)

        model.addAttribute("score", score)
        model.addAttribute("user", user)
        return "sec/manage/score"
    }

    /**
     * 保存用户活动积分
     * @param
     * @return
     */
    @RequestMapping(value = "/saveScore", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    private fun saveActivityScore(
            @ModelAttribute("score") score: ScoreHistory,
            redirectAttributes: RedirectAttributes): String {
        var score = score
        var user = this.sessionUser

        //取得用户信息
        user = userService!!.getUser(user!!.username)
        //验证用户账号
        if (user == null) {
            throw Exception("用户名不存在！")
        }

        //验证活动编号
        if (activityService!!.getActivity(score.activityId!!) == null) {
            throw Exception("活动编号不存在！")
        }

        if (score.id == null || score.id == 0) {
            var existScore = scoreService!!.getScoreHistories(user.id, score.activityId)
            if (existScore != null) {
                throw Exception("一个用户在同一个活动中只允许添加一次积分！")
            }
        }

        score.userId = user.id
        score = scoreService!!.save(score)

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/manage/scores"
    }

    /**
     * 删除用户活动积分
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteScore/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun deleteActivityScore(@PathVariable id: Int?, model: Model): String {
        //删除活动积分
        scoreService!!.deleteById(id)
        return "redirect:/sec/manage/scores"
    }


    /**
     * 用户缴费订单信息
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/payments", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun payments(request: HttpServletRequest,
                         model: Model): String {
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

        var status = request.getParameter("status")
        if (status.isNullOrBlank()) {
            status = "2" //完成付款
        }
        model.addAttribute("start", start)
        model.addAttribute("end", end)
        model.addAttribute("status", status)

        return "sec/admin/manage/payments"
    }

    /**
     * 平台订单RestController
     * @author Richard
     */
    @CrossOrigin
    @RequestMapping(value = "/getPayments", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun payments(@RequestBody(required = false) map: Map<String, String>): List<Any> {
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

        //用户所属团体
        var community_user = map["community_user"]

        //取得活动积分明细
        var items = oderService!!.getUserActivityPayments(start, end, activityId, title, username, real_name, mobile, ticket_title, extenal_id, status, refund_trade_no, refund_status, community_user)
        var list = items.intoMaps()
        return list
    }

    /**
     * 用户缴费订单信息
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/ordersstatistics", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun ordersstatistics(request: HttpServletRequest,
                                 model: Model): String {
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

        var title = request.getParameter("title")
        //取得用户缴费订单信息
        var items = oderService!!.getOrdersStatistics(title, start, end)
        var totalAmount: Double = 0.0
        for (item in items)
            totalAmount += java.lang.Double.parseDouble(item.getValue("amount").toString())

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        model.addAttribute("totalAmount", totalAmount)
        model.addAttribute("items", items)
        return "sec/manage/ordersstatistics"
    }
}
