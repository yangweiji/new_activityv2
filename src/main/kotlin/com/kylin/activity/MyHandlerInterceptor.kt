package com.kylin.activity

import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.CommunityService
import com.kylin.activity.service.ThirdUserService
import com.kylin.activity.service.UserService
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
        return true
    }

    //controller执行之后，且页面渲染之前调用
    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        //团体名称绑定
        var nameItems = communityService!!.getCommunities().sortedBy { it.id }

        if (modelAndView != null) {
            modelAndView!!.addObject("handlerCommunities", nameItems)
        }

//        if (!request!!.servletPath.startsWith("/pub/wx", true)) {
//            if (request!!.session.getAttribute("USER_CONTEXT") != null) {
//                var user = request!!.session.getAttribute("USER_CONTEXT") as User
//                //平台管理员
//                if (user.role == "管理员" || user.role == "发布员") {
//                    if (modelAndView != null) {
//                        modelAndView!!.addObject("canViewManagementCenter", true)
//                        modelAndView!!.addObject("canViewCommunity", true)
//                    }
//                }
//
//                if (request!!.session.getAttribute("COMMUNITY_CONTEXT") != null) {
//                    var community = request!!.session.getAttribute("COMMUNITY_CONTEXT") as Community
//                    var communityUser = communityService!!.getCommunityUser(community.id, user.id)
//                    //团体组织管理员或发布员
//                    if (communityUser != null && (communityUser.role == "管理员" || communityUser.role == "发布员")) {
//                        if (modelAndView != null) {
//                            modelAndView!!.addObject("canViewCommunity", true)
//                        }
//                    }
//                }
//            }
//        }
    }

    //页面渲染之后调用，一般用于资源清理操作
    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {

    }
}