package com.kylin.activity.controller

import com.kylin.activity.service.ActivityService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HomeController : BaseController() {

    @Autowired
    private val activityService: ActivityService? = null

    @GetMapping("/")
    fun index(@RequestParam(required = false) s: Int?, model: Model): String {
        //取得活动信息
        var activities = activityService!!.getPublicActivities()
        activities = when (s) {
        //人气最高
            2 -> activities.sortDesc("attend_user_count")
        //最新活动
            else -> activities.sortDesc("start_time")
        }

        model.addAttribute("s", if (s == 2) 2 else 1)
        model.addAttribute("activities", activities)

        return "index"
    }

}