package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.CommunityService
import com.kylin.activity.service.UserService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

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
     * 查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/communities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun afterCommunity(): String {
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
                      @ModelAttribute("user") user: User): String {
        if (community.id != null && community.id > 0) {
            var users = userService!!.getUser(community.managerPhoneNumber)
            if(users!!.username==null){
                   throw Exception("当前管理员联系电话不能修改")
            }
            users!!.displayname=community.controlName
            users!!.workCompany = community.company
            community.created=DateUtil.date().toTimestamp()
            communityService!!.updateUser(users)
            communityService!!.updateCommunity(community)
        } else {
            //取得用户信息
            var username = userService!!.getUser(community.managerPhoneNumber)
            if (username != null) {
                throw Exception("用户已存在！")
            }

            var code = BCryptPasswordEncoder()
            user!!.id = null
            user!!.password = code.encode("666666")
            user!!.enabled = true
            user!!.address = null
            user!!.workCompany = community.company
            user!!.username = community.managerPhoneNumber
            user!!.displayname = community.controlName
            user!!.created = DateUtil.date().toTimestamp()
            //isReal是否通过认证
            if (user.isReal == true) {
                user.realTime = DateUtil.date().toTimestamp()
            }
            userService!!.insert(user)

            community.id = null
            community.created = DateUtil.date().toTimestamp()
            communityService!!.insertCommunity(community)
            LogUtil.printLog("添加成功,管理员登录账号：${community.managerPhoneNumber}")
        }
        return "redirect:/sec/community/communities"
    }


    @RequestMapping(value = "/deleteCommunity/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteCommunity(@PathVariable("id") id: Int): String {
        communityService!!.deleteCommunity(id)
        return "redirect:/sec/community/communities"
    }

}