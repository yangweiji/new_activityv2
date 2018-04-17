package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.daos.VercodeDao
import com.kylin.activity.databases.tables.pojos.Vercode
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Timestamp
import java.time.Instant
import java.util.Random
import org.jooq.DSLContext
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("pub/wx/activity")
class WxActivityController {

    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val util: KylinUtil? = null

    @GetMapping("/search")
    fun search(@RequestParam(required = false) s:Int?, @RequestParam(required = false) t:String?): Any {

        var tag = if (t.isNullOrBlank()) "b5" else t!!

        var sid = if (s ==null) 0 else s!!

        var activities = activityService!!.getPublicActivities(tag, "","")
        //获取团队活动信息
        var teamactivities = activityService!!.getTeamActivities(sid, tag)

        var titems= mutableListOf<MutableMap<String, Any?>>()

        var items = mutableListOf<MutableMap<String, Any?>>()

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
            items.add(map)
        }

        for (activity in teamactivities){
            var map =mutableMapOf<String, Any?>()
            var avatar:String?=null
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
            titems.add(map)
        }


        return arrayOf(titems,items)
    }
}