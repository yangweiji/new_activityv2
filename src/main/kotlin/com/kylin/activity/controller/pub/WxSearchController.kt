package com.kylin.activity.controller.pub

import com.kylin.activity.config.ActivityProperties
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.ParseException

/**
 * Created by zk on 2018-07-21.
 */
@RestController
@RequestMapping("pub/wx/search")
class WxSearchController {
    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null
    /**
     * 通用服务
     */
    @Autowired
    private var commonService: CommonService? = null
    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 活动配置
     */
    @Autowired
    private val activityProperties: ActivityProperties? = null

    /**
     * 搜索活动
     * /pub/search/s;tag=0;time=0;pay=0
     *
     * @param tag: 活动标签
     * @param time: 活动时间
     * @param searchText: 搜索输入文本
     * @return
     */
    @RequestMapping(value = "/activities", method = arrayOf(RequestMethod.GET))
    @Throws(ParseException::class)
    private fun searchActivities(
            @RequestParam(defaultValue = "0") tag: String,
            @RequestParam(defaultValue = "0") time: String,
            @RequestParam(defaultValue = "") searchText: String
    ): Any {

        //取得活动信息
        var activities = activityService!!.getPublicActivities(tag, time, searchText)

        var result = mutableListOf<MutableMap<String, Any?>>()
        for (activity in activities) {
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
            result.add(map)
        }

        return result
    }
}