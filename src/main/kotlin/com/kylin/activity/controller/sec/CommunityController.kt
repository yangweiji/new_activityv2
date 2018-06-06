package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
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


@Controller
@RequestMapping("sec/community")
@SessionAttributes("user")
class CommunityController : BaseController() {
    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

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
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null


    /**
     * 活动相册服务
     */
    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val thirdUserService: ThirdUserService? = null

    /**
     * 查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/communities", method = [RequestMethod.GET, RequestMethod.POST])
    fun afterCommunity(model: Model, request: HttpServletRequest): String {
        var user = this.sessionUser
        model.addAttribute("user", user)

        return "sec/community/communities"
    }


    /**
     * 异步查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getCommunities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun getCommunities(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var name = map["name"]
        var items = communityService!!.queryCommunity(name)
        var list = items.intoMaps()
        return list
    }

    /**
     * 编辑或添加团体信息
     * @param id 团体编号
     */
    @GetMapping("/updateOraddcommunity")
    fun updateOraddcommunity(@RequestParam(required = false) id: Int?, model: Model, @ModelAttribute("community") community: Community): String {
        //user用户表，community 团体表，add时，分配给管理员role,password
        //community中管理员电话=usr中的displayname/username
        var communitiesData = CommunitiesData()
        if (id != null && id > 0) {
            communitiesData.community = communityService!!.getCommunityId(id)
        } else {
            communitiesData.community = Community()
        }
        model.addAttribute("communitiesData", communitiesData)
        return "sec/community/updateOraddcommunity"
    }

    /**
     * 保存更新或添加团体信息
     * @param community 团体
     * @param user 用户信息
     */
    @PostMapping("/updateOraddcommunity")
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
            communityService!!.updateCommunity(community)
            LogUtil.printLog("更新成功,团体组织ID：${community.id}")
        } else {
            //取得用户信息
            var u = userService!!.getUser(community.managerPhoneNumber)
            if (u != null) {
                var communitiesData = CommunitiesData()
                communitiesData.community = community
                model.addAttribute("communitiesData", communitiesData)
                model.addAttribute("errorMessage", "用户: ${community.managerPhoneNumber} 已存在！")
                return "/sec/community/updateOraddcommunity"
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
            communityService!!.insertCommunity(community)
            LogUtil.printLog("添加成功,团体组织ID：${community.id}")
        }
        return "redirect:/sec/community/communities"
    }


    /**
     * 删除团体信息前
     * 先判断活动，活动相册是否存在
     * @param id 团体编号,
     */
    @RequestMapping(value = "/deleteCommunity/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteCommunity(@PathVariable("id")id:Int,model: Model): String {

        //关联团体id
        var activity = activityService!!.getActivityCommunity(id)
        //关联活动id
        var activityPhoto=activityPhotoService!!.getActivityphoto(id)
        if (activity != null || activityPhoto!=null) {
            model.addAttribute("errorMessage", "当前团体有关联活动或相册，不能删除！")
            return "/sec/community/communities"
        }else{
            communityService!!.deleteCommunity(id)
        }
        return "redirect:/sec/community/communities"
    }

}