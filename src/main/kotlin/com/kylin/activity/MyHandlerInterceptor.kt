package com.kylin.activity

import com.kylin.activity.service.CommunityService
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
        var nameItems = communityService!!.getCommunities()
        if (modelAndView != null) {
            modelAndView!!.addObject("handlerCommunities", nameItems)
        }
    }

    //页面渲染之后调用，一般用于资源清理操作
    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {

    }
}