package com.kylin.activity

import com.kylin.activity.model.AuthUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by 9kylin on 2018-07-05.
 */
@Component
class AppAjaxAuthSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val session = request.session
        //清除全部session值
        session.removeAttribute("USER_CONTEXT")
        session.removeAttribute("COMMUNITY_CONTEXT")
        session.removeAttribute("COMMUNITY_USER_CONTEXT")

        val userDetails = authentication.principal as UserDetails
        //将登录用户信息存入session中
        session.setAttribute("USER_CONTEXT", (userDetails as AuthUser).user)

        //LogUtil.printLog("登录系统IP :" + getIpAddress(request))
        log.info("登录系统IP : {}", getIpAddress(request))
        super.onAuthenticationSuccess(request, response, authentication)

        response.status = HttpServletResponse.SC_OK
    }

    /**
     * 获取IP地址
     * @param request: 请求参数
     */
    fun getIpAddress(request: HttpServletRequest): String? {

        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }

        return ip
    }
}