package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.daos.VercodeDao
import com.kylin.activity.databases.tables.pojos.Vercode
import com.kylin.activity.model.MessageResult
import com.kylin.activity.service.VerCodeService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.JsonUtils
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Timestamp
import java.time.Instant
import java.util.Random
import org.jooq.DSLContext
import org.springframework.web.bind.annotation.*

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
        //commonService!!.sendSms(mobile, code)

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