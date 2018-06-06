package com.kylin.activity.service

import com.kylin.activity.model.AuthUser
import com.kylin.activity.util.LogUtil
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

/**
 * Created by 9kylin on 2017-12-04.
 * @author Richard C. Hu
 */
class AppSessionSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    /**
     * 认证成功
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Throws(ServletException::class, IOException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val session = request.session
        val userDetails = authentication.principal as UserDetails
        //将登录用户信息存入session中
        session.setAttribute("USER_CONTEXT", (userDetails as AuthUser).user)
        //将用户所在团体组织信息存入session中

        LogUtil.printLog("登录系统IP :" + getIpAddress(request))

        super.onAuthenticationSuccess(request, response, authentication)
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
