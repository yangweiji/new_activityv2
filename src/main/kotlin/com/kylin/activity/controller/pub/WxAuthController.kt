package com.kylin.activity.controller.pub

import com.kylin.activity.config.ActivityProperties
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.MessageResult
import com.kylin.activity.service.CommunityService
import com.kylin.activity.service.UserService
import com.kylin.activity.service.VerCodeService
import com.kylin.activity.service.WxService
import com.kylin.activity.sms.SmsTemplateListProperties
import com.kylin.activity.util.JsonUtils
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import me.chanjar.weixin.common.error.WxErrorException
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


/**
 * 小程序登录认证结果
 */
data class WxAuthResult(
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
 * 小程序用户手机号
 */
data class WxPhoneNumberInfo(
        var code: Int? = null,
        var phoneNumber: String? = null,
        var purePhoneNumber: String? = null,
        var countryCode: String? = null,
        var watermark: WaterMark? = null
)

/**
 * 小程序数据水印
 */
data class WaterMark(
        var timestamp: String? = null,
        var appid: String? = null
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

    /**
     * 短信配置
     */
    @Autowired
    private val templateListProperties: SmsTemplateListProperties? = null

    /**
     * 活动配置
     */
    @Autowired
    private val activityProperties: ActivityProperties? = null

    /**
     * 团体服务
     */
    @Autowired
    private var communityService: CommunityService? = null

    @GetMapping("/getSessionInfo")
    fun getSessionInfo(@RequestParam(required = false) code: String?): Any {

        return wxService!!.maService!!.userService!!.getSessionInfo(code)
    }

    /**
     * 退出
     * @param map: 参数
     * @return WxAuthResult
     */
    @PostMapping(value = "/logout")
    fun logout(@RequestBody(required = false) map: Map<String, String>): Any {
        var userId = map["userId"]!!.toInt()
        return userService!!.miniAppLogout(userId)
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
            //LogUtil.printLog(JsonUtils.toJson(session))

            var result = WxAuthResult()
            result.openid = session.openid
            result.sessionKey = session.sessionKey
            result.sessionId = session.openid + session.sessionKey
            result.unionId = session.unionid
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
    fun getUserInfo(openid: String?, unionId: String?): Any {
        var user = userService!!.getUserByOpenOrUnionId(openid, unionId)
        var community: Community? = null
        if (user != null) {
            if (user.openId != openid) {
                //设定小程序openid值
                user.openId = openid
                userService!!.update(user)
            }

            user!!.password = null
            //默认团体组织信息
            community = communityService!!.getDefaultCommunity(user.id)
            LogUtil.printLog("username: ${user.username}, openid: ${user.openId}, unionid: ${user.unionId}, community: ${community.id}")
        }

        return mapOf("user" to user, "community" to community)
    }

    /**
     * 获取小程序用户信息
     * @param sessionKey: 会话密钥
     * @param encryptedData: 消息密文
     * @param ivStr: 加密算法的初始向量
     * @return WxAuthResult
     */
    @GetMapping("/getMiniAppUserInfo")
    fun getMiniAppUserInfo(@RequestParam(required = true) sessionKey: String
                           , @RequestParam(required = true) encryptedData: String
                           , @RequestParam(required = true) ivStr: String): Any {

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
     * 获取小程序微信手机号码
     * @param sessionKey: 会话密钥
     * @param encryptedData: 消息密文
     * @param ivStr: 加密算法的初始向量
     * @return WxAuthResult
     */
    @GetMapping("/getMiniAppPhoneNumberInfo")
    fun getMiniAppPhoneNumberInfo(@RequestParam(required = true) sessionKey: String
                                  , @RequestParam(required = true) encryptedData: String
                                  , @RequestParam(required = true) ivStr: String): Any {

        return try {
            val phoneNumberInfo = this.wxService!!.maService!!.userService.getPhoneNoInfo(sessionKey, encryptedData, ivStr)
            var result = WxPhoneNumberInfo()
            result.phoneNumber = phoneNumberInfo.phoneNumber
            result.purePhoneNumber = phoneNumberInfo.purePhoneNumber
            result.countryCode = phoneNumberInfo.countryCode
            result.watermark = WaterMark(phoneNumberInfo.watermark.timestamp, phoneNumberInfo.watermark.appid)

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
        //var password = map["password"]
        var verCode = map["vercode"]
        //OpenId
        var openId = map["openId"]
        //unionId
        var unionId = map["unionId"]
        //用户昵称
        var nickName = map["nickName"]
        //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        var avatarUrl = map["avatarUrl"]
        //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        var gender = map["gender"]

        var messageResult = MessageResult()
        var verCodeInfo = verCodeService!!.getVerCode(username.toString(), verCode.toString())

        var coder = BCryptPasswordEncoder()
        //登录，短信有效期10分钟
        if (verCodeInfo != null && DateUtil.betweenMs(DateUtil.date(), verCodeInfo!!.created) <= templateListProperties!!.timeout) {
            var user = userService!!.checkUserExist(username, openId, unionId)
            if (user == null) {
                //添加用户信息
                var user = User()
                user.username = username
                user.mobile = username
                //初始密码: 123456
                user.password = coder.encode(activityProperties!!.defaultPassword)
                user.enabled = true
                user.created = DateUtil.date().toTimestamp()
                //显示名称与登录名一致
                user.displayname = nickName
                user.avatar = avatarUrl
                user.gender = gender!!.toInt()
                user.openId = openId
                user.unionId = unionId
                user.nickName = nickName

                userService!!.insert(user)
                LogUtil.printLog("注册用户OK, ID: ${user.id}")
            } else {
                user.username = username
                user.mobile = username
                if (user.password.isNullOrBlank()) {
                    //初始密码: 123456
                    user.password = coder.encode(activityProperties!!.defaultPassword)
                }

                //更新OpenId
                user!!.openId = openId
                user!!.unionId = unionId
                user!!.nickName = nickName
                if (user.displayname.isNullOrBlank()) {
                    //显示名称与登录名一致
                    user.displayname = nickName
                } else if (user.displayname.startsWith("1")) {
                    //以手机号码首位字符串"1"开头，则更新成为昵称
                    user.displayname = nickName
                }

                if (user.avatar.isNullOrBlank()) {
                    user.avatar = avatarUrl
                }
                if (user.gender == null) {
                    user.gender = gender!!.toInt()
                }

                userService!!.update(user)
                LogUtil.printLog("更新用户OK, ID: ${user.id}")
            }

            messageResult.code = 200
            messageResult.message = "SUCCESS"

        } else {
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