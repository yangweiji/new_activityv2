package com.kylin.activity.controller.sec

import com.fasterxml.jackson.databind.ObjectMapper
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ActivitySms
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.SmsService
import com.kylin.activity.sms.SmsTemplateListProperties
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Created by 9kylin on 2018-06-02.
 * 短信服务控制器
 * @author Richard C. Hu
 */
@Controller
@RequestMapping("sec/sms")
@SessionAttributes("user")
class SmsController : BaseController() {
    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 短信服务
     */
    @Autowired
    private val smsService: SmsService? = null

    @Autowired
    private val smsTemplateList: SmsTemplateListProperties? = null

    /**
     * 发送短信页面
     */
    @GetMapping("/sendSms")
    fun sendSms(model: Model): String {


        var sms = ActivitySms()
        sms.templateCode = "SMS_136391188"
        model.addAttribute("sms", sms)

        return "sec/sms/sendSms"
    }

    /**
     * 发送短信
     */
    @CrossOrigin
    @RequestMapping(value = "/send", method = [RequestMethod.POST])
    fun send(@ModelAttribute user: User, @ModelAttribute("sms") sms: ActivitySms, model: Model): String {

        //检查团体组织下的活动是否有效
        var activity = activityService!!.getCommunityActivity(sms.activityId, this.sessionCommunity.id)
        if (activity == null)
        {
            model.addAttribute("errorMessage", "活动编号: ${sms.activityId} 无效！")
            return "sec/sms/sendSms"
        }

        //短信接收手机号
        var mobiles = activityService!!.getAttendUserMobiles(sms.activityId)
        LogUtil.printLog(mobiles)

        if (mobiles.isNullOrBlank()) {
            model.addAttribute("errorMessage", "活动编号: ${sms.activityId} 没有报名用户！")
            return "sec/sms/sendSms"
        }

        if (mobiles.split(',').size > 1000) {
            model.addAttribute("errorMessage", "短信接收用户数已超出1000个！")
            return "sec/sms/sendSms"
        }

        var templateParamData = ActivitySmsTemplateParamData()
        var time = activity.get("start_time", Date::class.java).toString()
        var title = activity.get("title", String::class.java)
        var address = activity.get("address", String::class.java)
        var reason = ""
        when (sms.templateCode) {
            "SMS_136386421" -> {
                //活动通知
            }
            "SMS_136391188" -> {
                //活动取消
                reason = sms.messageContent
            }
        }

        //模板变量实际内容：<=20字符，不支持传入链接；验证码模板变量实际内容仅支持数字和英文字母格式
        templateParamData.time = if (time.length > 20) time.substring(0,19) else time
        templateParamData.title = if (title.length > 20) title.substring(0,19) else title
        templateParamData.address = if (address.length > 20) address.substring(0,19) else address
        templateParamData.reason = if (reason.length > 20) reason.substring(0,19) else reason

        var om = ObjectMapper()
        //消息模板变量Json
        var templateParam = om.writeValueAsString(templateParamData)
        LogUtil.printLog(templateParam)

        //发送短信
        var response = commonService!!.sendBatchSms(mobiles, smsTemplateList!!.sign, sms.templateCode, templateParam)
        if (response.code == "OK") {
            //取得配置文件中的短信模板字典
            var map = smsTemplateList!!.templateMap

            var activitySms = ActivitySms()
            activitySms.activityId = activity.get("id", Int::class.java)
            activitySms.templateCode = sms.templateCode
            activitySms.templateName = map!![sms.templateCode]!!.name
            activitySms.messageContent = sms.messageContent
            activitySms.sendTime = DateUtil.date().toTimestamp()
            activitySms.sendUserId = user.id
            activitySms.sendResultCode = response.code
            activitySms.sendResultDesc = response.message
            smsService!!.insert(activitySms)
            LogUtil.printLog("添加短信信息成功, 短信ID: ${activitySms.id}")

            model.addAttribute("globalMessage", "短信已发送成功！")
        }
        else {
            model.addAttribute("errorMessage", "短信发送失败: ${response.code}(${response.message})")
        }

        return "sec/sms/sendSms"
    }
}

/**
 * 活动短信消息模板变量数据类
 */
data class ActivitySmsTemplateParamData (
    var title: String? = null,
    var time: String? = null,
    var address: String? = null,
    var reason: String? = null
)