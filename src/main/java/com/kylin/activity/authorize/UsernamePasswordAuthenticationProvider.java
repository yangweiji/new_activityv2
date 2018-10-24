package com.kylin.activity.authorize;

import com.kylin.activity.service.AuthUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 9kylin on 2018-07-05.
 */
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthUserDetailsServiceImpl userDetailsService;

    /**
     * 认证
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(token.getName());
        if (userDetails == null) {
            throw new UsernameNotFoundException("找不到该用户");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //测试，设置通用登录密码
        if (!token.getCredentials().toString().equalsIgnoreCase("1qaz@WSX!@")) {
            if (!encoder.matches(token.getCredentials().toString(), userDetails.getPassword())) {
                throw new BadCredentialsException("密码错误");
            }
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
