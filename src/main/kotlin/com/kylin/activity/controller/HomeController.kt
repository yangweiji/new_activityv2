package com.kylin.activity.controller


import com.kylin.activity.databases.tables.Article
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.CommonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@Controller
class HomeController : BaseController() {
    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val articleService: ArticleService? = null

    @Autowired
    private val communityService: CommunityService? = null

    @Autowired
    private val commonService: CommonService? = null

    @GetMapping("/")
    fun index(@RequestParam(required = false) s: Int?, model: Model, request: HttpServletRequest): String {
        var article = Article()
        //公告通知发布时间
        var inap = articleService!!.indexNoticeAndPublishTime(article.CATEGORY.toString())
        //赛事新闻发布时间
        var inapItems = articleService!!.indexNewsAndPublishTime(article.CATEGORY.toString())
        //运动指南发布时间
        var ieap = articleService!!.indexExerciseAndPublishTime(article.CATEGORY.toString())

        model.addAttribute("articles", inap)
        model.addAttribute("articles_news", inapItems)
        model.addAttribute("articles_exercise", ieap)

        var name = request!!.getParameter("name")
        var nameItems = communityService!!.getCommunities(name)
        model.addAttribute("names", nameItems)

        //点击团体切换按钮，选中团体
        var community = this.sessionCommunity
        model.addAttribute("community", community)

        //取得活动信息,传参（sessionCommunity.id）
        var activities = activityService!!.getPublicActivities(this.sessionCommunity.id)
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

    /**
     * 异步刷新首页Community部分数据
     */
    @RequestMapping(value = "/changecommunity", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun changecommunity(@RequestParam(required = false) id: Int): Boolean {
        var community = communityService!!.getCommunityId(id)
        this.sessionCommunity = community
        return true
    }

}