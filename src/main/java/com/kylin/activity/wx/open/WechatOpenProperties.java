package com.kylin.activity.wx.open;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@ConfigurationProperties(prefix = "wechat.open")
public class WechatOpenProperties {
    /**
     * 设置微信三方平台的appid
     */
    private String componentAppId;

    /**
     * 设置微信三方平台的app secret
     */
    private String componentSecret;

    /**
     * 设置微信三方平台的token
     */
    private String componentToken;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String componentAesKey;

    /**
     * 设置网站应用的appid
     */
    private String webAppId;

    /**
     * 设置网站应用的app secret
     */
    private String webAppSecret;

    /**
     * 设置重定向URI
     */
    private String webRedirectUri;

    /**
     * 微信号绑定重定向URI
     */
    private String webWxBindUri;

    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getComponentSecret() {
        return componentSecret;
    }

    public void setComponentSecret(String componentSecret) {
        this.componentSecret = componentSecret;
    }

    public String getComponentToken() {
        return componentToken;
    }

    public void setComponentToken(String componentToken) {
        this.componentToken = componentToken;
    }

    public String getComponentAesKey() {
        return componentAesKey;
    }

    public void setComponentAesKey(String componentAesKey) {
        this.componentAesKey = componentAesKey;
    }

    public String getWebAppId() {
        return webAppId;
    }

    public void setWebAppId(String webAppId) {
        this.webAppId = webAppId;
    }

    public String getWebAppSecret() {
        return webAppSecret;
    }

    public void setWebAppSecret(String webAppSecret) {
        this.webAppSecret = webAppSecret;
    }

    public String getWebRedirectUri() {
        return webRedirectUri;
    }

    public void setWebRedirectUri(String webRedirectUri) {
        this.webRedirectUri = webRedirectUri;
    }

    public String getWebWxBindUri() {
        return webWxBindUri;
    }

    public void setWebWxBindUri(String webWxBindUri) {
        this.webWxBindUri = webWxBindUri;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
