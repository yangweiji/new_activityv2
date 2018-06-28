package com.kylin.activity.controller

import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * Created by 9kylin on 2017-12-04.
 * @author Richard C. Hu
 */
@Controller
class BaseController {

    @Autowired
    private var communityDao: CommunityDao? = null

    /**
     * 通用服务
     */
    @Autowired
    private var commonService: CommonService? = null

    /**
     * 团体服务
     */
    @Autowired
    private var communityService: CommunityService? = null

    /**
     * 分页每页记录数
     */
    val PAGE_SIZE: Int = 12
    /**
     * 用户上下文
     */
    val USER_CONTEXT: String = "USER_CONTEXT"

    /**
     * 用户Session
     */
    protected var sessionUser: User?
        get() {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val session = request.session
            return if (session.getAttribute(USER_CONTEXT) != null) (session.getAttribute(USER_CONTEXT) as User) else null
        }
        set(user) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            request.session.setAttribute("USER_CONTEXT", user)


        }

    /***
     * 团体组织Session
     */
    protected var sessionCommunity: Community
        get() {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val session = request.session
            var user = session.getAttribute("USER_CONTEXT") as User?
            var result = session.getAttribute("COMMUNITY_CONTEXT") as Community?
            if (result == null) {

                var communityUser = CommunityUser()
                if (user != null) {
                    //如果当前用户是团体组织管理员，设置当前团体组织信息
                    communityUser = communityService!!.getCommunityAdminUser(user!!.id)!!

                    //删除COMMUNITY_USER_CONTEXT
                    request.session.removeAttribute("COMMUNITY_USER_CONTEXT")
                    if (communityUser != null && communityUser.communityId != null) {
                        request.session.setAttribute("COMMUNITY_USER_CONTEXT", communityUser)
                    }
                }

                result = if (user != null && user!!.role != "管理员" && communityUser != null && communityUser.communityId != null) {
                    communityService!!.getCommunity(communityUser!!.communityId)
                } else {
                    communityDao!!.fetchOneById(1)
                }

                result!!.avatar = commonService!!.getDownloadUrl(result!!.avatar)
                result!!.background = commonService!!.getDownloadUrl(result!!.background)

                sessionCommunity = result!!
            }

//            //获取团体图标
//            var avatar = commonService!!.getDownloadUrl(result!!.avatar)
//            session.setAttribute("COMMUNITY_AVATAR", avatar)
//
//            //获取团体背景图片
//            var background = commonService!!.getDownloadUrl(result!!.background)
//            session.setAttribute("COMMUNITY_BACKGROUND", background)

            return result!!
        }
        set(community) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            request.session.setAttribute("COMMUNITY_CONTEXT", community)
        }


}
