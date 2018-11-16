package com.kylin.activity.controller.pub

import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.PosterService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 微信海报相关控制器
 */
@RestController
@RequestMapping("pub/wx/home")
class WxHomeController {

    /**
     * 通用服务
     */
    @Autowired
    private var commonService: CommonService? = null


    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 海报服务
     */
    @Autowired
    private val posterService: PosterService?=null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null



    /**
     * 查询指定团体组织的活动信息
     * @param communityId: 团体组织标识
     * @param t: 活动标签分类
     * @return 团体组织活动信息集合
     */
    @GetMapping("/search")
    fun search(@RequestParam(required = false) communityId: Int, @RequestParam(required = false) t: String?): Any {
        return searchActivities(communityId, t)
    }

    /**
     * 微信首页，获取海报信息 + 默认活动信息
     */
    @GetMapping("/data")
    fun getHomeData(@RequestParam(required = false) communityId: Int, @RequestParam(required = false) t: String?): Any{
        var posters = getPosters()
        var activityies = searchActivities(communityId, t)
        return mapOf("posters" to posters, "activities" to activityies)
    }

    /**
     * 微信首页，获取海报信息 + 默认活动信息
     */
    @GetMapping("/getData")
    fun getData(@RequestParam(required = false) communityId: Int, @RequestParam(required = false) t: String?, @RequestParam(required = false) p: Int, @RequestParam(required = false) n: Int): Any{
        var posters = getPosters()
        var activityies = searchActivities(communityId, t, p, n)
        return mapOf("posters" to posters, "activities" to activityies)
    }

    /**
     * 查询指定团体组织的活动信息
     * @param communityId: 团体组织标识
     * @param t: 活动标签分类
     * @return 团体组织活动信息集合
     */
    fun searchActivities(communityId: Int, t: String?): Any {
        //活动标签分类:默认为【训练】
        var tag = if (t.isNullOrBlank()) "b5" else t!!
        //获取团体组织活动信息
        var teamActivities = activityService!!.getTeamActivities(communityId, tag)
        //团体组织活动信息
        var teamActivityItems = mutableListOf<MutableMap<String, Any?>>()
        for (activity in teamActivities.sortDesc("start_time")) {
            var map = mutableMapOf<String, Any?>()
            var avatar: String? = null
            if (activity["avatar"] != null) {
                avatar = commonService!!.getDownloadUrl(activity.get("avatar", String::class.java), "middle")
            }
            map["id"] = activity.get("id", Int::class.java)
            map["activity_type"] = activity.get("activity_type", Int::class.java)
            map["favorite_count"] = activity.get("favorite_count", Int::class.java)
            map["attend_count"] = activity.get("attend_user_count", Int::class.java)
            map["avatar"] = avatar
            map["start_time"] = util!!.fromNow(activity.get("start_time"))
            map["title"] = activity.get("title").toString()
            teamActivityItems.add(map)
        }

        return teamActivityItems
    }

    /**
     * 查询指定团体组织的活动信息
     * @param communityId: 团体组织标识
     * @param t: 活动标签分类
     * @param p: 分页索引
     * @param n: 记录数
     * @return 团体组织活动信息集合
     */
    fun searchActivities(communityId: Int, t: String?, p: Int, n: Int): Any {
        //活动标签分类:默认为【训练】
        var tag = if (t.isNullOrBlank()) "b5" else t!!
        //获取团体组织活动信息
        var teamActivities = activityService!!.getTeamActivities(communityId, tag, p, n)
        //团体组织活动信息
        var teamActivityItems = mutableListOf<MutableMap<String, Any?>>()
        for (activity in teamActivities.sortDesc("start_time")) {
            var map = mutableMapOf<String, Any?>()
            var avatar: String? = null
            if (activity["avatar"] != null) {
                avatar = commonService!!.getDownloadUrl(activity.get("avatar", String::class.java), "middle")
            }
            map["id"] = activity.get("id", Int::class.java)
            map["activity_type"] = activity.get("activity_type", Int::class.java)
            map["favorite_count"] = activity.get("favorite_count", Int::class.java)
            map["attend_count"] = activity.get("attend_user_count", Int::class.java)
            map["avatar"] = avatar
            map["start_time"] = util!!.fromNow(activity.get("start_time"))
            map["title"] = activity.get("title").toString()
            teamActivityItems.add(map)
        }

        return teamActivityItems
    }

    /**
     * 获取海报信息
     */

    private fun getPosters():Any{
        var posters=posterService!!.getTopPosters()
        var posterItems = mutableListOf<MutableMap<String, Any?>>()
        for (poster in posters) {
            var map = mutableMapOf<String, Any?>()
            var activityId = poster.get("activity_id",Int::class.java)
            if(activityId != null && activityId > 0) { //小程序只显示活动海报
                map["activity_id"] = activityId
                var avatar: String? = null
                if (poster["avatar"] != null) {
                    avatar = commonService!!.getDownloadUrl(poster.get("avatar", String::class.java), "middle")
                }
                var mobileAvatar: String? = null
                if (mobileAvatar == null) {
                    mobileAvatar = avatar
                }
                if (poster["mobile_avatar"] != null) {
                    mobileAvatar = commonService!!.getDownloadUrl(poster.get("mobile_avatar", String::class.java), "middle")
                }
                map["id"] = poster.get("id", Int::class.java)
                map["title"] = poster.get("title").toString()
                map["created"] = util!!.fromNow(poster.get("created"))
                map["link"] = poster.get("link").toString()
                map["avatar"] = avatar
                map["mobile_avatar"] = mobileAvatar
                map["poster_type"] = poster.get("poster_type").toString()
                map["show"] = poster.get("show", Boolean::class.java)
                map["sequence"] = poster.get("sequence", Int::class.java)
                posterItems.add(map)
            }
        }
        return posterItems
    }


}