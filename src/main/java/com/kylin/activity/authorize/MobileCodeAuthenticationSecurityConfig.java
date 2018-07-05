package com.kylin.activity.authorize;

import com.kylin.activity.AppSessionSuccessHandler;
import com.kylin.activity.filter.MobileCodeAuthenticationProcessingFilter;
import com.kylin.activity.service.AuthUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by 9kylin on 2018-07-05.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration("MobileCodeAuthenticationSecurityConfig")
public class MobileCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private static final Logger logger = LoggerFactory.getLogger(MobileCodeAuthenticationSecurityConfig.class.getName());

    @Autowired
    private AuthUserDetailsServiceImpl userDetailsService;

    @Autowired
    private AppSessionSuccessHandler customAuthenticationSuccessHandler;

    public MobileCodeAuthenticationSecurityConfig() {
        logger.info("MobileCodeAuthenticationSecurityConfig loading ...");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String url = "/login/mobile";
        String parameter = "mobile";
        String httpMethod = "POST";

        MobileCodeAuthenticationProcessingFilter filter = new MobileCodeAuthenticationProcessingFilter(
                url,
                parameter,
                httpMethod);

        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);

        MobileCodeAuthenticationProvider provider = new MobileCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
