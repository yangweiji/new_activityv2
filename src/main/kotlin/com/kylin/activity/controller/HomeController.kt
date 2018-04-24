package com.kylin.activity.controller

import com.kylin.activity.databases.tables.Article
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import javax.websocket.server.PathParam

@Controller
class HomeController : BaseController() {

    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val articleService:ArticleService?=null

    @GetMapping("/")
    fun index(@RequestParam(required = false) s: Int?, model: Model): String {
        var article=Article()
        var inap = articleService!!.indexNoticeAndPublishTime(article.CATEGORY.toString())
        var inapItems=articleService!!.indexNewsAndPublishTime(article.CATEGORY.toString())
        var ieap=articleService!!.indexExerciseAndPublishTime(article.CATEGORY.toString())
        model.addAttribute("articles", inap)
        model.addAttribute("articles_news",inapItems)
        model.addAttribute("articles_exercise",ieap)
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