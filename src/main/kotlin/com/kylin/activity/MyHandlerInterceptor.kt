package com.kylin.activity

import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyHandlerInterceptor : HandlerInterceptor {
    @Autowired
    private var communityService: CommunityService? = null

    constructor(communityService: CommunityService?) {
        this.communityService = communityService
    }


    //在Controller执行之前调用，如果返回false，controller不执行
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        //检查团体组织管理用户是否可以跨团体组织操作
        if (request != null && request.servletPath.startsWith("/sec/community/", true)) {
            if (request.session.getAttribute("USER_CONTEXT") != null) {
                var user = request.session.getAttribute("USER_CONTEXT") as User
                var community = request.session.getAttribute("COMMUNITY_CONTEXT") as Community
                var communityUser = communityService!!.getCommunityUser(user.id, community.id)

                //平台管理员
                if (!user!!.role.isNullOrEmpty()) {
                    return true
                }

                //当前用户在当前团体组织下为普通用户
                if (communityUser == null || communityUser!!.role.isNullOrEmpty()) {
                    LogUtil.printLog("${request.servletPath} 操作无权限！")

                    //重定向至首页
                    response!!.sendRedirect("/")
                    return false
                }
            }
        }

        return true
    }

    //controller执行之后，且页面渲染之前调用
    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        //团体名称绑定
        var nameItems = communityService!!.getCommunities().sortedBy { it.id }

        if (modelAndView != null) {
            modelAndView!!.addObject("handlerCommunities", nameItems)
        }
    }

    //页面渲染之后调用，一般用于资源清理操作
    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {

    }
}