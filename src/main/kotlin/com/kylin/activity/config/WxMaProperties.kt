package com.kylin.activity.config
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author [Binary Wang](https://github.com/binarywang)
 */
@ConfigurationProperties(prefix = "wechat.miniapp")
class WxMaProperties {
    /**
     * 设置微信小程序的appid
     */
    var appid: String? = null

    /**
     * 设置微信小程序的Secret
     */
    var secret: String? = null

    /**
     * 设置微信小程序的token
     */
    var token: String? = null

    /**
     * 设置微信小程序的EncodingAESKey
     */
    var aesKey: String? = null

    /**
     * 消息格式，XML或者JSON
     */
    var msgDataFormat: String? = null

    var baseUrl:String? = null
    /**
     * 微信小程序报名Page
     */
    var attendPage: String? = null

    /**
     * 微信小程序报名签到Page
     */
    var checkInPage: String? = null

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }


}
