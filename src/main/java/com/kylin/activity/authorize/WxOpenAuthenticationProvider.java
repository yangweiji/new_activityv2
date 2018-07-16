package com.kylin.activity.authorize;

import com.kylin.activity.service.AuthUserDetailsServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by 9kylin on 2018-07-05.
 */
public class WxOpenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(WxOpenAuthenticationProvider.class.getName());

    @Getter
    @Setter
    @Autowired
    public AuthUserDetailsServiceImpl userDetailsService;

    public WxOpenAuthenticationProvider() {
        logger.info("WxOpenAuthenticationProvider loading ...");
    }

    /**
     * 认证
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取过滤器封装的token信息
        WxOpenAuthenticationToken token = (WxOpenAuthenticationToken) authentication;
        //获取用户信息（数据库认证）
        UserDetails userDetails = userDetailsService.loadUserByUnionId((String) token.getPrincipal());
        if (userDetails == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }

        //通过
        WxOpenAuthenticationToken authenticationResult = new WxOpenAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(token.getDetails());

        return authenticationResult;
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WxOpenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
