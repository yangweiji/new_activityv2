package com.kylin.activity.controller

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.service.CommunityService

/**
 * Created by 9kylin on 2017-12-04.
 */
@Controller
open class BaseController {

    @Autowired
    private var communityDao: CommunityDao? = null

    @Autowired
    private var commonService: CommonService? = null

    @Autowired
    private var communityService: CommunityService? = null

    protected var sessionUser: User
        get() {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val session = request.session
            return session.getAttribute(USER_CONTEXT) as User
        }
        set(user) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            request.session.setAttribute("USER_CONTEXT", user)
        }

    protected var sessionCommunity: Community
        get() {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val session = request.session
            var result = session.getAttribute("COMMUNITY_CONTEXT") as Community?
            if (result == null) {
                result = communityDao!!.fetchOneById(1)
                sessionCommunity = result
            }
            //获取团体图标
            var avatar = commonService!!.getDownloadUrl(result!!.avatar)
            session.setAttribute("COMMUNITY_AVATAR", avatar)

            //获取团体背景图片
            var background = commonService!!.getDownloadUrl(result!!.background)
            session.setAttribute("COMMUNITY_BACKGROUND", background)
            return result!!
        }
        set(community) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            request.session.setAttribute("COMMUNITY_CONTEXT", community)
        }


    fun getAppbaseUrl(request: HttpServletRequest, url: String): String {
        Assert.hasLength(url, "url不能为空")
        Assert.isTrue(url.startsWith("/"), "必须以/开头")
        return request.contextPath + url
    }

    val PAGE_SIZE: Int = 12
    val USER_CONTEXT: String = "USER_CONTEXT"

}
