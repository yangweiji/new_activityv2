package com.kylin.activity

import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Bean
fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
}

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration //Make this as a configuration class
@EnableWebSecurity //Turn on Web Security
class WebSecurityConfig
    : WebSecurityConfigurerAdapter(){

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    /**
     * 安全验证配置处理
     */
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/", "/index", "/changeCommunity", "/pub/**", "/static/**", "/asset/**", "/js/**", "/css/**", "/fonts/**", "/img/**", "/images/**", "/json/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .permitAll()
        http.csrf().disable()

        //session管理
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login")
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login")
    }

    /**
     * 登录成功之后
     */
    private fun loginSuccessHandler(): AppSessionSuccessHandler {
        return AppSessionSuccessHandler()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder())
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false)
    }
}