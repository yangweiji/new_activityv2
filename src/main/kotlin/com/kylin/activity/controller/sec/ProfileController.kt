package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.MessageResult
import com.kylin.activity.service.*
import com.kylin.activity.util.JsonUtils
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import me.chanjar.weixin.common.error.WxErrorException
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken
import me.chanjar.weixin.mp.bean.result.WxMpUser
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

class ProfileUpdateProperty {
    val property: String? = null
    val value: Any? = null
}

/**
 * 个人中心、基本信息控制器
 */
@Controller
@RequestMapping("sec/user")
@SessionAttributes("user")
class ProfileController : BaseController() {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

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

    @Autowired
    private val wxService: WxService? = null

    /**
     * 短信验证码服务
     */
    @Autowired
    private val verCodeService: VerCodeService? = null

    /**
     * 向隐含模型中添加user的模型属性
     */
    @ModelAttribute("user")
    fun getUser(): User? {
        return this.sessionUser
    }

    /**
     * 个人信息
     */
    @CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
    @RequestMapping("/profile")
    fun profile(@ModelAttribute("user") user: User, model: Model): String {

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
    @CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
    @RequestMapping("/favorite")
    fun favorite(@ModelAttribute("user") user: User, model: Model): String {

        var activities = activityService!!.getUserFavoriteActivities(user!!.id)

        model.addAttribute("items", activities)
        return "sec/user/favorite"
    }

    /**
     * 个人积分
     */
    @RequestMapping(value = "/personalscore", method = [RequestMethod.GET, RequestMethod.POST])
    private fun getPersonalScore(@ModelAttribute("user") user: User, model: Model): String {
        var community = this.sessionCommunity
        //取得活动积分明细
        val items = scoreService!!.getPersonalScore(user.id!!, community.id!!)
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
    @RequestMapping(value = "/personalpayment", method = [RequestMethod.GET, RequestMethod.POST])
    private fun getPersonalPayment(@ModelAttribute("user") user: User, model: Model): String {
        //取得缴费订单
        val items = orderService!!.getPersonalPayment(user.id!!)

        model.addAttribute("items", items)
        return "sec/user/personalpayment"
    }

    /**
     * 基本信息
     */
    @RequestMapping(value = "/baseinfo", method = [RequestMethod.GET, RequestMethod.POST])
    private fun getBaseInfo(@ModelAttribute("user") user: User): String {

        return "sec/user/baseinfo"
    }

    /**
     * 账户安全
     */
    @RequestMapping(value = "/account", method = [RequestMethod.GET, RequestMethod.POST])
    private fun getAccount(@ModelAttribute("user") user: User): String {

        return "sec/user/account"
    }

    /**
     * 绑定微信号
     */
    @RequestMapping(value = "/bindwx", method = [RequestMethod.GET, RequestMethod.POST])
    private fun bindWechat(@ModelAttribute("user") user: User
                           , model: Model
                           , request: HttpServletRequest
                           , redirectAttributes: RedirectAttributes): String {
        var code = request.getParameter("code")
        var state = request.getParameter("state")
        log.info("code: {}, state: {}", code, state)
        var wxMpOAuth2AccessToken: WxMpOAuth2AccessToken? = null
        var wxMpUser: WxMpUser? = null

        //检查state是有效的
        if (request.session.getAttribute("WECHAT_STATE").toString() != state) {
            LogUtil.printLog("wechat state is invalid!")
        }

        try {
            //取得AccessToken
            wxMpOAuth2AccessToken = wxService!!.mpService!!.oauth2getAccessToken(code)
            LogUtil.printLog("wxMpOAuth2AccessToken: " + wxMpOAuth2AccessToken!!.toString())

            wxMpUser = wxService!!.mpService!!.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN")
            LogUtil.printLog("wxMpUser: " + wxMpUser!!.toString())

            //检查微信账号是否已绑定
            var u = userService!!.getUserByUnionId(wxMpUser.unionId)
            if (u != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "微信账号: ${wxMpUser.nickname} 已被绑定，如需绑定当前登录用户，请先微信扫码登录平台解绑或注销账户！")
                return "redirect:/sec/user/account"
            }

            //user.openId = wxMpUser.openId
            user.unionId = wxMpUser.unionId
            user.nickName = wxMpUser.nickname
            if (user.avatar.isNullOrBlank()) {
                user.avatar = wxMpUser.headImgUrl
            }
            if (user.gender == null) {
                user.gender = wxMpUser.sex
            }

            userService!!.update(user)
            log.info("更新用户OK: ${user.id}")

            this.sessionUser = user

            redirectAttributes.addFlashAttribute("globalMessage", "操作成功！微信账号: ${wxMpUser.nickname} 已成功绑定当前用户！")
            return "redirect:/sec/user/account"
        } catch (e: WxErrorException) {
            e.printStackTrace()
        }

        return "redirect:/sec/user/account"
    }

    /**
     * 解除绑定
     */
    @RequestMapping(value = "/unbindwx", method = [RequestMethod.GET, RequestMethod.POST])
    private fun unbindWechat(@ModelAttribute("user") user: User
                             , model: Model
                             , redirectAttributes: RedirectAttributes): String {
        var nickName = user.nickName

        user.nickName = null
        //user.openId = null
        user.unionId = null
        userService!!.update(user)
        log.info("更新用户OK: ${user.id}")

        this.sessionUser = user

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！微信账号: $nickName 已成功解绑！")
        return "redirect:/sec/user/account"
    }

    /**
     * 更新个人信息
     */
    @RequestMapping(value = "/update", method = [RequestMethod.GET, RequestMethod.POST])
    private fun update(@ModelAttribute("user") user: User, model: Model, request: HttpServletRequest): String {
        var u = userService!!.getUser(user.id)

        u.displayname = user.displayname
        u.gender = user.gender
        u.realName = user.realName
        u.idCard = user.idCard
        if (!user.idCard.isNullOrBlank() && !user.realName.isNullOrBlank()) {
            u.isReal = true
            u.realTime = DateUtil.date().toTimestamp()
        }
        u.wechatId = user.wechatId
        u.email = user.email
        u.bloodType = user.bloodType
        u.clothingSize = user.clothingSize
        u.workCompany = user.workCompany
        u.occupation = user.occupation
        u.emergencyContactName = user.emergencyContactName
        u.emergencyContactMobile = user.emergencyContactMobile
        u.isParty = user.isParty
        u.address = user.address

        userService!!.update(u)
        log.info("更新用户OK: ${u.id}")

        model.addAttribute("globalMessage", "操作成功！")

        this.sessionUser = u
        return "/sec/user/baseinfo"
    }

    /**
     * 注销用户信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deletewx", method = [RequestMethod.GET, RequestMethod.POST])
    fun deleteWechat(@ModelAttribute("user") user: User, request: HttpServletRequest): String {
        if (user.id != null && user.id > 0) {
            userService!!.deleteById(user.id)
            //删除用户session
            request.session.removeAttribute("USER_CONTEXT")
        }

        return "redirect:/logout"
    }

    /**
     * 添加手机号
     */
    @CrossOrigin
    @RequestMapping(value = "/addMobile", method = [RequestMethod.POST])
    @ResponseBody
    fun addMobile(@ModelAttribute("user") user: User
                  , model: Model
                  , request: HttpServletRequest
                  , @RequestBody(required = false) map: Map<String, String>): String {
        var mobile = map["mobile"].toString()
        var smsCode = map["smsCode"].toString()
        var password = map["password"].toString()

        var messageResult = MessageResult()
        var verCode = verCodeService!!.getVerCode(mobile, smsCode)
        when {
            verCode == null -> {
                messageResult.code = -2
                messageResult.message = "无效的短信验证码，请重新获取"
            }
            userService!!.getUser(mobile) != null -> {
                messageResult.code = -3
                messageResult.message = "该手机号已绑定平台其他用户，请更换手机号码"
            }
            userService!!.addMobile(user.id, mobile, password) -> {
                //设定手机号码
                user.username = mobile
                user.mobile = mobile

                this.sessionUser = user
                messageResult.code = 200
                messageResult.message = "SUCCESS"
            }
            else -> {
                messageResult.code = -1
                messageResult.message = "添加登录手机号操作失败"
            }
        }

        return JsonUtils.toJson(messageResult)
    }


    /**
     * 更换手机号码
     */
    @CrossOrigin
    @RequestMapping(value = "/updateMobile", method = [RequestMethod.POST])
    @ResponseBody
    fun updateMobile(@ModelAttribute("user") user: User
                     , model: Model
                     , request: HttpServletRequest
                     , @RequestBody(required = false) map: Map<String, String>): String {
        var mobile = map["mobile"].toString()
        var smsCode = map["smsCode"].toString()
        var newMobile = map["newMobile"].toString()
        var smsCode2 = map["smsCode2"].toString()

        var messageResult = MessageResult()

        when {
            user.mobile != mobile -> {
                messageResult.code = -4
                messageResult.message = "原手机号输入不正确"
            }
            userService!!.getUser(newMobile) != null -> {
                messageResult.code = -3
                messageResult.message = "新手机号码已绑定平台其他用户，请更换新手机号码"
            }
            userService!!.updateMobile(user.id, mobile, smsCode, newMobile, smsCode2) -> {
                //设定手机号码
                user.username = newMobile
                user.mobile = newMobile

                this.sessionUser = user
                messageResult.code = 200
                messageResult.message = "SUCCESS"
            }
            else -> {
                messageResult.code = -1
                messageResult.message = "验证码无效，请重新获取验证码更换手机号码"
            }
        }

        return JsonUtils.toJson(messageResult)
    }

    /**
     * 更新登录密码
     */
    @CrossOrigin
    @RequestMapping(value = "/changePassword2", method = [RequestMethod.POST])
    @ResponseBody
    fun changePassword2(@ModelAttribute("user") user: User
                        , model: Model
                        , request: HttpServletRequest
                        , @RequestBody(required = false) map: Map<String, String>): String {
        var oldPassword = map["oldPassword"].toString()
        var password = map["password"].toString()

        var messageResult = MessageResult()
        var coder = BCryptPasswordEncoder()
        //读取原密码
        var userPassword = userService!!.getUser(user.id).password
        //验证原密码是否匹配
        if (!coder.matches(oldPassword, userPassword)) {
            messageResult.code = -1
            messageResult.message = "旧密码输入不正确"
        } else {
            user.password = coder.encode(password)
            userService!!.update(user)
            messageResult.code = 200
            messageResult.message = "SUCCESS"

            this.sessionUser = user
        }

        return JsonUtils.toJson(messageResult)
    }
}