package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * 团体数据类
 */
data class CommunitiesData(
        var community: Community? = null,
        var user: User? = null
)


/**
 * 团体组织控制器
 * @author weiji.yang
 */
@Controller
@RequestMapping("sec/admin/community")
@SessionAttributes("user")
class CommunityController : BaseController() {

    /**
     * 团体服务
     */
    @Autowired
    private val communityService: CommunityService? = null


    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/communities", method = [RequestMethod.GET, RequestMethod.POST])
    fun afterCommunity(model: Model, request: HttpServletRequest): String {
        var user = this.sessionUser
        model.addAttribute("user", user)

        return "sec/admin/community/communities"
    }


    /**
     * 异步查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getCommunities", method = [RequestMethod.GET, RequestMethod.POST])
    @ResponseBody
    fun getCommunities(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var name = map["name"]
        var items = communityService!!.queryCommunity(name)
        return items.intoMaps()
    }

    /**
     * 编辑或添加团体信息
     * @param id 团体编号
     * @return 编辑或添加页面
     */
    @GetMapping("/community")
    fun community(@RequestParam(required = false) id: Int?
                  , model: Model
                  , @ModelAttribute("community") community: Community): String {
        var communitiesData = CommunitiesData()
        if (id != null && id > 0) {
            communitiesData.community = communityService!!.getCommunity(id)
        } else {
            communitiesData.community = Community()
        }
        model.addAttribute("communitiesData", communitiesData)

        return "sec/admin/community/community"
    }

    /**
     * 保存更新或添加团体信息
     * @param community 团体
     * @param user 用户信息
     */
    @PostMapping("/saveCommunity")
    @Transactional
    fun saveCommunity(@ModelAttribute("community") community: Community,
                      @ModelAttribute("user") user: User
                      , model: Model): String {

        var code = BCryptPasswordEncoder()
        if (community.id != null && community.id > 0) {
            //团体组织已存在，编辑团体组织信息
            //检查该团体组织是否已经有管理员
            var communityUser = userService!!.getCommunityUser(community.id)
            if (communityUser != null) {
                //更新该管理的信息
                communityUser.username = community.managerPhoneNumber
                communityUser.displayname = community.controlName
                communityUser.workCompany = community.company
                userService!!.update(communityUser)
                LogUtil.printLog("更新成功,管理员ID：${communityUser.id}")
            } else {
                //添加团体组织管理员
                //构建一个新User对象
                var u = User()
                u.username = community.managerPhoneNumber
                u.password = code.encode("000000")
                u.displayname = community.controlName
                u.workCompany = community.company
                u.enabled = true
                u.created = DateUtil.date().toTimestamp()
                u.isReal = true
                u.realTime = DateUtil.date().toTimestamp()
                userService!!.insert(u)
                LogUtil.printLog("添加成功,管理员ID：${u.id}")

                //构建一个新团体组织用户关联对象
                var communityUser = CommunityUser()
                communityUser.communityId = community.id
                communityUser.userId = u.id
                communityUser.role = "管理员"
                communityUser.level = DateUtil.thisYear()
                communityUser.created = DateUtil.date().toTimestamp()
                userService.insertCommunityUser(communityUser)
                LogUtil.printLog("添加成功,团体组织用户关联ID：${communityUser.id}")
            }

            //更新团体组织信息
            community.created = DateUtil.date().toTimestamp()
            community.createdBy = user!!.id
            communityService!!.update(community)
            LogUtil.printLog("更新成功,团体组织ID：${community.id}")
        } else {
            //取得用户信息
            var u = userService!!.getUser(community.managerPhoneNumber)
            if (u != null) {
                var communitiesData = CommunitiesData()
                communitiesData.community = community
                model.addAttribute("communitiesData", communitiesData)
                model.addAttribute("errorMessage", "用户: ${community.managerPhoneNumber} 已存在！")
                return "/sec/admin/community/community"
            }
            //构建一个新的User对象
            u = User()
            u.username = community.managerPhoneNumber
            u!!.displayname = community.controlName
            u.password = code.encode("000000")
            u!!.workCompany = community.company
            u.enabled = true
            u.isReal = true
            u.realTime = DateUtil.date().toTimestamp()
            u!!.created = DateUtil.date().toTimestamp()
            userService!!.insert(u)
            LogUtil.printLog("添加成功,管理员ID：${u.id}")

            //构建一个新团体组织用户关联对象
            var communityUser = CommunityUser()
            communityUser.communityId = community.id
            communityUser.userId = u.id
            communityUser.role = "管理员"
            communityUser.level = DateUtil.thisYear()
            communityUser.created = DateUtil.date().toTimestamp()
            userService.insertCommunityUser(communityUser)
            LogUtil.printLog("添加成功,团体组织用户关联ID：${communityUser.id}")

            community.created = DateUtil.date().toTimestamp()
            communityService!!.insert(community)
            LogUtil.printLog("添加成功,团体组织ID：${community.id}")
        }

        return "redirect:/sec/admin/community/communities"
    }


    /**
     * 删除团体信息
     * @return 检查团体组织下是否有关联的活动
     * 有返回-1，无返回0
     */
    @RequestMapping(value = "/deleteCommunity", method = [RequestMethod.POST])
    @ResponseBody
    fun deleteCommunity(request: HttpServletRequest, model: Model): Any? {

        var communityId = if (request.getParameter("communityId") != null)
            request.getParameter("communityId").toInt() else 0

        if (communityService!!.checkCommunity(communityId)) {
            return -1
        } else {
            communityService!!.deleteCommunity(communityId)
        }

        return 0

    }

}