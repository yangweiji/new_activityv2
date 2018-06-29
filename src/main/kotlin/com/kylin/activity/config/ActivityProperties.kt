package com.kylin.activity.config
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Richard C. Hu
 */
@Component
@ConfigurationProperties(prefix = "activity")
class ActivityProperties {
    /**
     * 默认登录密码
     */
    var defaultPassword: String? = null


    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }


}
