package com.kylin.activity

import com.kylin.activity.authorize.MobileCodeAuthenticationProvider
import com.kylin.activity.authorize.UsernamePasswordAuthenticationProvider
import com.kylin.activity.filter.MobileCodeAuthenticationProcessingFilter
import com.kylin.activity.service.AuthUserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Bean
fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
}

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration //Make this as a configuration class
@EnableWebSecurity //Turn on Web Security
@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
class WebSecurityConfig
    : WebSecurityConfigurerAdapter(){

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun mobileCodeAuthenticationProcessingFilter(): MobileCodeAuthenticationProcessingFilter {
        val filter = MobileCodeAuthenticationProcessingFilter()
        filter.setAuthenticationManager(authenticationManager)
        return filter
    }

    @Bean
    fun usernamePasswordAuthenticationProvider(): UsernamePasswordAuthenticationProvider {
        return UsernamePasswordAuthenticationProvider()
    }

    @Bean
    fun mobileCodeAuthenticationProvider(): MobileCodeAuthenticationProvider {
        return MobileCodeAuthenticationProvider()
    }

    @Autowired
    private val userDetailsService: AuthUserDetailsServiceImpl? = null

    /**
     * Ajax Authentication Success Handler
     */
    @Autowired
    private val customAuthenticationSuccessHandler: AppAjaxAuthSuccessHandler? = null

    /**
     * Ajax Authentication Failure Handler
     */
    @Autowired
    private val customAuthenticationFailHandler: AppAjaxAuthFailHandler? = null

    /**
     * 安全验证配置处理
     */
    override fun configure(http: HttpSecurity) {
        //添加过滤器
        //http.addFilterBefore(mobileCodeAuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter::class.java)

        var filter = mobileCodeAuthenticationProcessingFilter()
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler)
        filter.setAuthenticationFailureHandler(customAuthenticationFailHandler)
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter::class.java)

        http.authorizeRequests()
                .antMatchers("/"
                        , "/index"
                        , "/login"
                        , "/login/**"
                        , "/changeCommunity"
                        , "/*.txt"
                        , "/pub/**"
                        , "/static/**"
                        , "/asset/**"
                        , "/js/**"
                        , "/css/**"
                        , "/fonts/**"
                        , "/img/**"
                        , "/images/**"
                        , "/json/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/index")
                .permitAll()

        http.csrf().disable()

        //session管理
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login")
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login")
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(mobileCodeAuthenticationProvider())
                .authenticationProvider(usernamePasswordAuthenticationProvider())
    }
}