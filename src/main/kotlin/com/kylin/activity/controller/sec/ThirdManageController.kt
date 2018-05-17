package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ScoreHistory
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("sec/thirdmanage")
class ThirdManageController : BaseController() {
    /**
     * 订单服务
     */
    @Autowired
    private val oderService: ThirdOrderService? = null

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val activityService: ThirdActivityService? = null

    @Autowired
    private val scoreService: ThirdScoreService? = null

    @Autowired
    private val userService: UserService? = null

    /**
     * 第三方管理中心，最新活动查询页面
     *
     * @param status: 活动状态
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/home/{status}", method = arrayOf(RequestMethod.GET))
    private fun home(@PathVariable status: Int?, model: Model): String {
        //取得最新的活动记录
        var activityItems = activityService!!.getAllActivityUserItemsAndCommunity(status!!, this.sessionCommunity.id)
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
        return "sec/thirdmanage/home"
    }

    /**
     *  第三方查询用户积分
     */
    @CrossOrigin
    @RequestMapping(value = "/scores", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun scores(@ModelAttribute score: ScoreHistory, request: HttpServletRequest, model: Model): String {
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
        return "sec/thirdmanage/scores"
    }

    /**
     * 异步查询用户积分
     */
    @CrossOrigin
    @RequestMapping(value = "/getScores", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun scores(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var title = map["title"]
        var username = map["username"]
        var real_name = map["real_name"]

        //取得活动积分明细
        var items = scoreService!!.getUserActivityScores(start, end, title, username, real_name, this.sessionCommunity.id)
        var list = items.intoMaps()
        return list
    }

    /**
     *
     *  第三方删除用户积分
     */
    @RequestMapping(value = "/deleteScore/{id}", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun deleteActivityScore(@PathVariable id: Int?): String {
        scoreService!!.deleteById(id)
        return "redirect:/sec/thirdmanage/scores"
    }

    /**
     * 第三方编辑用户积分
     */
    @RequestMapping(value = "/score/{id}", method = arrayOf(RequestMethod.GET))
    private fun editActivityScore(@ModelAttribute user: User, @ModelAttribute score: ScoreHistory, @PathVariable id: Int?, model: Model): String {
        var score = scoreService!!.getScoreHistory(id)
        var user = userService!!.getUser(score.userId!!)
        model.addAttribute("score", score)
        model.addAttribute("user", user)
        return "sec/thirdmanage/score"
    }

    /**
     * 第三方添加用户积分
     */
    @RequestMapping(value = "/score", method = arrayOf(RequestMethod.GET))
    @Throws(Exception::class)
    private fun createActivityScore(@ModelAttribute user: User, @ModelAttribute score: ScoreHistory, model: Model): String {
        model.addAttribute("user", user)
        model.addAttribute("score", score)
        return "sec/thirdmanage/score"
    }

    /**
     * 保存修改及添加用户积分
     * 验证用户账号及编号是否存在
     */
    @RequestMapping(value = "/saveScore", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    private fun saveActivityScore(@ModelAttribute user: User?, @ModelAttribute score: ScoreHistory, redirectAttributes: RedirectAttributes): String {
        var score = score
        var user = user

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
        return "redirect:/sec/thirdmanage/scores"
    }

    /**
     * 用户缴费订单信息
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/payments", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun payments(model: Model, request: HttpServletRequest): String {
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
        return "sec/thirdmanage/payments"
    }

    /**
     * 订单管理异步查找积分信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getPayments", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun payments(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var title = map["title"]
        var username = map["username"]
        var real_name = map["real_name"]

        //取得活动积分明细
        var items = oderService!!.getUserActivityPayments(start, end, title, username, real_name)
        var list = items.intoMaps()
        return list
    }

    /**
     * 用户订单统计信息
     */
    @CrossOrigin
    @RequestMapping(value = "/ordersstatistics",method = arrayOf(RequestMethod.GET,RequestMethod.POST))
     private fun ordersstatistics(request: HttpServletRequest,model: Model):String{
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank())
        {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank())
        {
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
        return "sec/thirdmanage/ordersstatistics"
    }
}