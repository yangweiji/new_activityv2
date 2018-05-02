package com.kylin.activity.controller

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.Article
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.CommonService
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import sun.plugin.liveconnect.SecurityContextHelper
import javax.servlet.http.HttpServletRequest


class CommunityUpdateProperty {
    val property: String? = null
    val value: Any? = null
}

@Controller
class HomeController : BaseController() {
    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val articleService: ArticleService? = null

    @Autowired
    private val communityService: CommunityService? = null

    @Autowired
    private val create:DSLContext?=null

    @GetMapping("/")
    fun index(@RequestParam(required = false) s: Int?, model: Model, request: HttpServletRequest): String {
        var article = Article()
        var inap = articleService!!.indexNoticeAndPublishTime(article.CATEGORY.toString())
        var inapItems = articleService!!.indexNewsAndPublishTime(article.CATEGORY.toString())
        var ieap = articleService!!.indexExerciseAndPublishTime(article.CATEGORY.toString())

        var name = request.getParameter("name")
        var nameItems = communityService!!.getCommunity(name)
        model.addAttribute("names", nameItems)
        model.addAttribute("articles", inap)
        model.addAttribute("articles_news", inapItems)
        model.addAttribute("articles_exercise", ieap)
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


    @GetMapping("/indexes/{id}")
    fun indexes(@RequestParam(required = false) s: Int?, model: Model, request: HttpServletRequest,
                @PathVariable("id") id: Int): String {
        var article = Article()
        var inap = articleService!!.indexNoticeAndPublishTime(article.CATEGORY.toString())
        var inapItems = articleService!!.indexNewsAndPublishTime(article.CATEGORY.toString())
        var ieap = articleService!!.indexExerciseAndPublishTime(article.CATEGORY.toString())
        var name = request.getParameter("name")
        var nameItems = communityService!!.getCommunity(name)
        model.addAttribute("names", nameItems)
        model.addAttribute("articles", inap)
        model.addAttribute("articles_news", inapItems)
        model.addAttribute("articles_exercise", ieap)
        //切换团体
        var changeItems = communityService!!.changeCommunities(id)
        if (changeItems["background"] != null) {
            changeItems.setValue(Tables.COMMUNITY.BACKGROUND,commonService!!.getDownloadUrl(changeItems.get("background",String::class.java)) )
        }
        if(changeItems["avatar"]!=null){
            changeItems.setValue(Tables.COMMUNITY.AVATAR,commonService!!.getDownloadUrl(changeItems.get("avatar",String::class.java)) )
        }
        model.addAttribute("communities",changeItems)



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

        return "/indexes"
    }


    /**
     * 异步刷新首页Community部分数据
     */
    @RequestMapping("/communityproperties")
    @ResponseBody
    fun ajaxCommunity(@RequestBody property:CommunityUpdateProperty){
        var communities = communityService!!.editCommunity(this.sessionCommunity.id)
        this.sessionCommunity=communities
    }
}