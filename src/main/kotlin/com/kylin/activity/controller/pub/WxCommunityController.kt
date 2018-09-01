package com.kylin.activity.controller.pub

import com.kylin.activity.service.CommunityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by 9kylin on 2018-06-12.
 */
@RestController
@RequestMapping("pub/wx/community")
class WxCommunityController {

    /**
     * 团体组织服务
     */
    @Autowired
    val communityService: CommunityService? = null

    /**
     * 取得唯一的团体组织信息
     */
    @CrossOrigin
    @RequestMapping(value = "/get", method = [RequestMethod.POST, RequestMethod.GET])
    fun get(communityId: Int): Any {
        return communityService!!.getCommunity(communityId)
    }

    /**
     * 取得唯一的团体组织信息
     */
    @CrossOrigin
    @RequestMapping(value = "/detail", method = [RequestMethod.POST, RequestMethod.GET])
    fun getCommunityDetail(@RequestBody(required = false) map: Map<String, String>): Any {
        var communityId = map["communityId"]!!.toInt()

        return communityService!!.getCommunity(communityId)
    }

    /**
     * 取得最新团体组织信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getLatestCommunities", method = [RequestMethod.POST, RequestMethod.GET])
    fun getLatestCommunities(): Any? {
        return communityService!!.getCommunities().sortedByDescending { it.created }
    }

    /**
     * 取得当前用户加入的团体组织信息
     * @param userId: 用户ID
     */
    @CrossOrigin
    @RequestMapping(value = "/getJoinedCommunities", method = [RequestMethod.POST, RequestMethod.GET])
    fun getJoinedCommunities(userId: Int): Any? {
        return communityService!!.getUserCommunities(userId)
    }


    /**
     * 团体组织排行榜
     */
    @CrossOrigin
    @RequestMapping(value = "/getCommunityTop", method = [RequestMethod.POST, RequestMethod.GET])
    fun getCommunityTop(): Any? {
        return communityService!!.getCommunities().sortedByDescending { it.countPeople }
    }


    /**
     * 取得团体组织
     */
    @CrossOrigin
    @RequestMapping(value = "/search", method = [RequestMethod.POST, RequestMethod.GET])
    fun search(userId: Int, status: Int): Any? {
        when (status) {
            //最新热门
            0 -> {
                return communityService!!.getCommunities().sortedByDescending { it.created }
            }
            //我加入的
            1 -> {
                return communityService!!.getCommunitiesByUser(userId)
            }
            //排行榜
            2 -> {
                return communityService!!.getCommunities().sortedByDescending { it.countPeople }
            }
        }

        return null
    }

    /**
     * 设置为默认的团体组织
     */
    @CrossOrigin
    @RequestMapping(value = "/default", method = [RequestMethod.POST, RequestMethod.GET])
    fun setDefault(@RequestBody(required = false) map: Map<String, String>): Boolean {
        var userId = map["userId"]!!.toInt()
        var communityId = map["communityId"]!!.toInt()

        return communityService!!.setDefault(userId, communityId)
    }

}