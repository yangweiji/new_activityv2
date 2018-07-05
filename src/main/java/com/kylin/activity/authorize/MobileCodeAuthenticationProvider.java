package com.kylin.activity.authorize;

import com.kylin.activity.databases.tables.pojos.Vercode;
import com.kylin.activity.service.AuthUserDetailsServiceImpl;
import com.kylin.activity.service.VerCodeService;
import com.kylin.activity.sms.SmsTemplateListProperties;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by 9kylin on 2018-07-05.
 */
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MobileCodeAuthenticationProvider.class.getName());

    @Getter
    @Setter
    @Autowired
    public AuthUserDetailsServiceImpl userDetailsService;

    @Autowired
    private VerCodeService verCodeService;

    /**
     * 短信配置
     */
    @Autowired
    private SmsTemplateListProperties templateListProperties;

    public MobileCodeAuthenticationProvider() {
        logger.info("MobileCodeAuthenticationProvider loading ...");
    }

    /**
     * 认证
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取过滤器封装的token信息
        MobileCodeAuthenticationToken token = (MobileCodeAuthenticationToken) authentication;
        //获取用户信息（数据库认证）
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) token.getPrincipal());
        if (userDetails == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }

        Vercode vercode = verCodeService.getVerCode(token.getPrincipal().toString(), token.getVerCode());
        if (vercode == null || DateUtil.betweenMs(DateUtil.date(), vercode.getCreated()) > templateListProperties.getTimeout()) {
            throw new BadCredentialsException("Sms Code InValid!");
        }

        //通过
        MobileCodeAuthenticationToken authenticationResult = new MobileCodeAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(token.getDetails());

        return authenticationResult;
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
