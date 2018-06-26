package com.kylin.activity.sms

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Created by 9kylin on 2018-06-06.
 * 短信模板集合
 * @author Richard C. Hu
 */
@Component
@ConfigurationProperties(prefix = "sms")
@Validated
class SmsTemplateListProperties {

    /**
     * 验证码过期
     */
    var timeout: Long = 0L

    /**
     * 短信签名
     */
    var sign: String = ""

    /**
     * 短信模板列表集合
     */
    @NotNull
    @NotEmpty
    @Valid
    var templates: List<SmsTemplateProperties>? = null

    /**
     * 短信模板字典数据
     */
    var templateMap: Map<String?, SmsTemplateProperties>? = null
        get() {
            var m = mutableMapOf<String?, SmsTemplateProperties>()
            this.templates!!.forEach {
                m[it.code] = it
            }
            return m
        }
}