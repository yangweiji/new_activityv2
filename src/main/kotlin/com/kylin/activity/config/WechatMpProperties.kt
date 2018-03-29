package com.kylin.activity.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * wechat mp properties
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@ConfigurationProperties(prefix = "wechat.mp")
class WechatMpProperties {
    /**
     * 设置微信公众号的appid
     */
    var appId: String? = null

    /**
     * 设置微信公众号的app secret
     */
    var secret: String? = null

    /**
     * 设置微信公众号的token
     */
    var token: String? = null

    /**
     * 设置微信公众号的EncodingAESKey
     */
    var aesKey: String? = null

    /**
     * 设置微信公众号的基本url
     */
    var baseUrl: String? = null

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE)
    }
}
