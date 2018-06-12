package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.MessageResult
import com.kylin.activity.service.UserService
import com.kylin.activity.service.VerCodeService
import com.kylin.activity.service.WxService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import me.chanjar.weixin.common.exception.WxErrorException
import com.kylin.activity.util.JsonUtils
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping

/**
 * 小程序登录认证结果
 */
data class WxAuthResult (
        var openid: String? = null,
        var sessionKey: String? = null,
        var sessionId: String? = null,
        var code: Int? = null,
        var isNeedUserInfo: Boolean = false,

        var nickName: String? = null,
        var avatarUrl: String? = null,
        var gender: String? = null,
        var city: String? = null,
        var province: String? = null,
        var country: String? = null,
        var language: String? = null,
        var unionId: String? = null
)

/**
 * 小程序用户认证控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/auth")
class WxAuthController {

    /**
     * 微信服务
     */
    @Autowired
    private val wxService: WxService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 验证码服务
     */
    @Autowired
    private val verCodeService: VerCodeService? = null

    @GetMapping("/getSessionInfo")
    fun getSessionInfo(@RequestParam(required = false) code: String?): Any {

        return wxService!!.maService!!.userService!!.getSessionInfo(code)
    }

    /**
     * 登录
     * @param code: 临时登录凭证code
     * @return WxAuthResult
     */
    @GetMapping("/login")
    fun login(@RequestParam(required = true) code: String): Any {
        if (StringUtils.isBlank(code)) {
            return "empty jscode"
        }

        return try {
            val session = this.wxService!!.maService!!.userService.getSessionInfo(code)
            LogUtil.printLog("sessionKey: ${session.sessionKey}")
            LogUtil.printLog("openId: ${session.openid}")

            //JsonUtils.toJson(session)

            var result = WxAuthResult()
            result.openid = session.openid
            result.sessionKey = session.sessionKey
            result.sessionId = session.openid + session.sessionKey
            result.code = 200
            result.isNeedUserInfo = true

            JsonUtils.toJson(result)
        } catch (e: WxErrorException) {
            LogUtil.printLog(e, WxAuthController::class.java)
            e.toString()
        }
    }

    /**
     * 取得用户信息
     * @param openid： 小程序用户openid
     * @return 返回用户信息
     */
    @GetMapping("/getUserInfo")
    fun getUserInfo(openid: String): User {
        var user = userService!!.getUserByOpenId(openid)
        user!!.password = null

        return user
    }

    /**
     * 登录
     * @param sessionKey: 会话密钥
     * @param encryptedData: 消息密文
     * @param ivStr: 加密算法的初始向量
     * @return WxAuthResult
     */
    @GetMapping("/getMiniAppUserInfo")
    fun getMiniAppUserInfo(@RequestParam(required = true) sessionKey: String
                    ,@RequestParam(required = true) encryptedData: String
                    ,@RequestParam(required = true) ivStr: String): Any {

        return try {
            val userInfo = this.wxService!!.maService!!.userService.getUserInfo(sessionKey, encryptedData, ivStr)
            var result = WxAuthResult()
            result.openid = userInfo.openId
            result.nickName = userInfo.nickName
            result.avatarUrl = userInfo.avatarUrl
            result.gender = userInfo.gender
            result.city = userInfo.city
            result.province = userInfo.province
            result.country = userInfo.country
            result.language = userInfo.language
            result.unionId = userInfo.unionId

            result.code = 200

            JsonUtils.toJson(result)
        } catch (e: WxErrorException) {
            LogUtil.printLog(e, WxAuthController::class.java)
            e.toString()
        }
    }

    /**
     * 验证用户身份
     * @param openId: 小程序用户唯一标识openid
     */
    @RequestMapping(value = "/validate", method = [RequestMethod.POST])
    fun validate(@RequestParam(required = true) openId: String): Any {
        var user = userService!!.getUserByOpenId(openId)
        return user != null
    }


    /**
     * 用户登录（手机号+密码+短信验证码）
     */
    @RequestMapping(value = "/userLogin", method = [RequestMethod.POST])
    fun userLogin(@RequestBody(required = false) map: Map<String, String>): Any {
        var username = map["username"]
        var password = map["password"]
        var verCode = map["vercode"]
        var openId = map["openId"]

        var messageResult = MessageResult()
        var verCodeInfo = verCodeService!!.getVerCode(username.toString(), verCode.toString())

        //登录，短信有效期10分钟
        if (verCodeInfo != null && DateUtil.betweenMs(DateUtil.date(), verCodeInfo!!.created) <= 1000 * 60 * 10) {
            var user = userService!!.getUser(username.toString())
            if (user == null) {
                messageResult.code = -2
                messageResult.message = "用户名无效！"
                return JsonUtils.toJson(messageResult)
            }

            var coder = BCryptPasswordEncoder()
            if (coder.matches(password, user!!.password)){
                //更新openid
                user!!.openId = openId
                userService!!.update(user)
                LogUtil.printLog("更新用户OK, ID: ${user.id}")

                messageResult.code = 200
                messageResult.message = "SUCCESS"
            }
            else {
                messageResult.code = -3
                messageResult.message = "密码无效！"
            }
        }
        else {
            messageResult.code = -1
            messageResult.message = "短信验证码无效或已过期！"
        }

        return JsonUtils.toJson(messageResult)
    }


    /**
     * 用户注册（手机号+密码+短信验证码）
     */
    @RequestMapping(value = "/register", method = [RequestMethod.POST])
    fun register(@RequestBody(required = false) map: Map<String, String>): Any {
        var username = map["username"].toString()
        var password = map["password"].toString()
        var verCode = map["vercode"].toString()
        var openId = map["openId"].toString()
        var nickName = map["nickName"].toString()
        var avatarUrl = map["avatarUrl"].toString()

        var messageResult = MessageResult()
        var result = userService!!.register(username, password, openId, verCode, nickName, avatarUrl)

        when (result) {
            "success" -> {
                messageResult.code = 200
                messageResult.message = "SUCCESS"
            }
            "vercode" -> {
                messageResult.code = -1
                messageResult.message = "短信验证码无效或已过期！"
            }
            "exist" -> {
                messageResult.code = -2
                messageResult.message = "该手机号已注册！"
            }
        }

        return JsonUtils.toJson(messageResult)
    }
}