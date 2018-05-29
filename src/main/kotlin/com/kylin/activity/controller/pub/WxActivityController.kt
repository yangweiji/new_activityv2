package com.kylin.activity.controller.pub

import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 微信活动相关控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/activity")
class WxActivityController {

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 查询指定团体组织的活动信息
     * @param s: 团体组织标识
     * @param t: 活动标签分类
     * @return 团体组织活动信息集合
     */
    @GetMapping("/search")
    fun search(@RequestParam(required = false) s: Int?, @RequestParam(required = false) t: String?): Any {
        //活动标签分类:默认为【训练】
        var tag = if (t.isNullOrBlank()) "b5" else t!!
        //团体组织标识
        var sid = if (s == null) 0 else s!!
        //获取团体组织活动信息
        var teamActivities = activityService!!.getTeamActivities(sid, tag)
        //团体组织活动信息
        var teamActivityItems = mutableListOf<MutableMap<String, Any?>>()
        for (activity in teamActivities) {
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
     * 显示活动详情内容
     * @param detailId: 活动ID
     * @return 单个活动信息
     */
    @GetMapping("/detail/{id}")
    fun detail(@RequestParam(required = false) detailId: Int?): Any {
        var activity = activityService!!.getActivityDetail(detailId)


        return activity
    }
}