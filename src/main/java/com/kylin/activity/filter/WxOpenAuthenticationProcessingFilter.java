package com.kylin.activity.filter;

/**
 * Created by 9kylin on 2018-07-05.
 */

import com.kylin.activity.authorize.WxOpenAuthenticationToken;
import com.kylin.activity.databases.tables.pojos.User;
import com.kylin.activity.service.UserService;
import com.kylin.activity.service.WxService;
import com.kylin.activity.util.LogUtil;
import com.kylin.activity.wx.mp.WechatMpProperties;
import com.kylin.activity.wx.open.WechatOpenProperties;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WxOpenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_UNIONID_KEY = "unionId";
    public static final String SPRING_SECURITY_FORM_OPENID_KEY = "openId";

    private String unionIdParameter = SPRING_SECURITY_FORM_UNIONID_KEY;
    private String openIdParameter = SPRING_SECURITY_FORM_OPENID_KEY;

    @Getter
    @Setter
    private String unionIdParameterName;

    /**
     * 微信开放平台配置
     */
    @Autowired
    private WechatOpenProperties wechatOpenProperties;

    /**
     * 微信公众平台配置
     */
    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Autowired
    private WxService wxService;

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    public WxOpenAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login/wx", "GET"));
    }

    public WxOpenAuthenticationProcessingFilter(String mobileLoginUrl, String unionIdParameterName,
                                                String httpMethod) {
        super(new AntPathRequestMatcher(mobileLoginUrl, httpMethod));
        this.unionIdParameterName = unionIdParameterName;
        logger.info("WxOpenAuthenticationProcessingFilter loading ...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        if (code == null) {
            code = "";
        }
        String state = request.getParameter("state");
        if (state == null) {
            state = "";
        }

        if (request.getSession().getAttribute("WECHAT_STATE") == null
                || !request.getSession().getAttribute("WECHAT_STATE").toString().equals(state)
                || code.isEmpty()) {
            LogUtil.printLog("State or Code is invalid!");
            return null;
        }

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        WxMpUser wxMpUser = null;

        //检查state是有效的
        //取得AccessToken
        try {
            wxMpOAuth2AccessToken = wxService.getMpService().oauth2getAccessToken(code);
            LogUtil.printLog("wxMpOAuth2AccessToken: " + wxMpOAuth2AccessToken.toString());

            wxMpUser = wxService.getMpService().oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            LogUtil.printLog("wxMpUser: " + wxMpUser.toString());

        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        User user = userService.getUserByUnionId(wxMpUser.getUnionId());
        if (user == null) {
            user = new User();
            user.setEnabled(true);
            user.setCreated(DateUtil.date().toTimestamp());
            user.setUsername(wxMpUser.getUnionId());
            user.setUnionId(wxMpOAuth2AccessToken.getUnionId());
            user.setOpenId(wxMpOAuth2AccessToken.getOpenId());
            user.setGender(wxMpUser.getSex());
            user.setDisplayname(wxMpUser.getNickname());
            user.setAvatar(wxMpUser.getHeadImgUrl());

            userService.insert(user);
            LogUtil.printLog("添加用户成功: " + user.getId());
        }

        WxOpenAuthenticationToken authRequest = new WxOpenAuthenticationToken(wxMpUser.getUnionId(), wxMpUser.getOpenId());

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
