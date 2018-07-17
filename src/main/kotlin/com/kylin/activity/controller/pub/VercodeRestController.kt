package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.Vercode
import com.kylin.activity.model.MessageResult
import com.kylin.activity.service.VerCodeService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.JsonUtils
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.time.Instant
import java.util.*

/**
 * 短信验证码
 */
@RestController
@RequestMapping("pub/wx/vercode")
class VercodeRestController {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 验证码服务
     */
    @Autowired
    private val verCodeService: VerCodeService? = null

    /**
     * 获取短信验证码
     * @param mobile: 手机号码
     * @return 是否成功
     */
    @RequestMapping("/getVerCode/{mobile}")
    fun getVerCode(@PathVariable mobile: String): Any {
        val random = Random()
        var code = (random.nextInt(900000) + 100000).toString()
        LogUtil.printLog("短信验证码: $code")
        //发送短信
        commonService!!.sendSms(mobile, code, 2)

        var verCode = Vercode()
        verCode.mobile = mobile
        verCode.created = Timestamp.from(Instant.now())
        verCode.code = code

        verCodeService!!.insert(verCode)

        var messageResult = MessageResult()
        messageResult.code = 200
        messageResult.message = code

        return JsonUtils.toJson(messageResult)
    }

    /**
     * 获取短信验证码
     * @param mobile: 手机号码
     * @return 是否成功
     */
    @RequestMapping("/getVerCode")
    fun getVerCode(@RequestParam mobile: String, @RequestParam templateId: Int): Any {
        val random = Random()
        var code = (random.nextInt(900000) + 100000).toString()
        LogUtil.printLog("短信验证码: $code")
        //发送短信
        commonService!!.sendSms(mobile, code, templateId)

        var verCode = Vercode()
        verCode.mobile = mobile
        verCode.created = Timestamp.from(Instant.now())
        verCode.code = code

        verCodeService!!.insert(verCode)

        var messageResult = MessageResult()
        messageResult.code = 200
        messageResult.message = code

        return JsonUtils.toJson(messageResult)
    }

}