package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ScoreService
import com.kylin.activity.service.OrderService
import com.kylin.activity.service.UserService
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

class ProfileUpdateProperty {
    val property: String? = null
    val value: Any? = null
}

@Controller
@RequestMapping("sec/user")
@SessionAttributes("user")
class ProfileController : BaseController() {

    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val scoreService: ScoreService? = null

    @Autowired
    private val orderService: OrderService? = null

    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val userService: UserService? = null

    /**
     * 个人信息
     */
    @CrossOrigin( origins = arrayOf("*"), methods= arrayOf(RequestMethod.GET, RequestMethod.POST ))
    @RequestMapping("/profile")
    fun profile(model: Model): String {
        var user = this.sessionUser
        model.addAttribute("user", user)
        var activities = create!!.select(Tables.ACTIVITY.fields().toList())
                .from(Tables.ACTIVITY)
                .innerJoin(Tables.ACTIVITY_USER)
                .on(Tables.ACTIVITY.ID.eq(Tables.ACTIVITY_USER.ACTIVITY_ID).and(Tables.ACTIVITY_USER.USER_ID.eq(user!!.id)))
                .fetch()
        model.addAttribute("items", activities)

        return "sec/user/profile"
    }

    /**
     * 修改个人基本信息
     */
    @RequestMapping("/updateproperty")
    @ResponseBody
    fun update(@RequestBody property: ProfileUpdateProperty) {
        val auth = SecurityContextHolder.getContext().authentication
        val name = auth.name //get logged in username

        create!!.update(Tables.USER)
                .set(DSL.field(property.property, Tables.USER), property.value)
                .where(Tables.USER.USERNAME.eq(name)).execute()
        var user = userService!!.getUser(this.sessionUser!!.id)
        this.sessionUser = user

    }

    /**
     * 我的收藏
     */
    @CrossOrigin( origins = arrayOf("*"), methods= arrayOf(RequestMethod.GET, RequestMethod.POST ))
    @RequestMapping("/favorite")
    fun favorite(@ModelAttribute user: User, model: Model): String {

        var activities = activityService!!.getUserFavoriteActivities(user!!.id)

        model.addAttribute("items", activities)
        return "sec/user/favorite"
    }

    /**
     * 个人积分
     */
    @RequestMapping(value = "/personalscore", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun getPersonalScore(@ModelAttribute user: User, model: Model): String {
        //取得活动积分明细
        val items = scoreService!!.getPersonalScore(user.id!!)
        var totalscore: Int? = 0
        for (r in items) {
            totalscore = totalscore!!.plus(r.getValue("score") as Int)
        }

        model.addAttribute("items", items)
        model.addAttribute("totalscore", totalscore)
        return "sec/user/personalscore"
    }

    /**
     * 个人缴费
     */
    @RequestMapping(value = "/personalpayment", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    private fun getPersonalPayment(@ModelAttribute user: User, model: Model): String {
        //取得缴费订单
        val items = orderService!!.getPersonalPayment(user.id!!)

        model.addAttribute("items", items)
        return "sec/user/personalpayment"
    }
}