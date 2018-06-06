package com.kylin.activity.sms

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull

/**
 * Created by 9kylin on 2018-06-06.
 * 短信模板属性
 * @author Richard C. Hu
 */
@Component
class SmsTemplateProperties {
    /**
     * 短信模板编码
     */
    @NotNull
    @NotEmpty
    var code: String? = null

    /**
     * 短信模板名称
     */
    @NotNull
    @NotEmpty
    var name: String? = null

    /**
     * 短信模板内容
     */
    @NotNull
    @NotEmpty
    var template: String? = null
}