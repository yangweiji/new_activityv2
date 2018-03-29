package com.kylin.activity.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * wxpay pay properties
 *
 * @author Binary Wang
 */
@ConfigurationProperties(prefix = "wechat.pay")
class WxPayProperties {
    /**
     * 设置微信公众号的appid
     */
    var appId: String? = null

    /**
     * 微信支付商户号
     */
    var mchId: String? = null

    /**
     * 微信支付商户密钥
     */
    var mchKey: String? = null

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    var subAppId: String? = null

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    var subMchId: String? = null

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    var keyPath: String? = null

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE)
    }
}
