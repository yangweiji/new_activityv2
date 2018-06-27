package com.kylin.activity.controller

import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import com.kylin.activity.service.CommunityService
import com.kylin.activity.service.PosterService
import com.kylin.activity.util.CommonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * 首页控制器
 * @author Richard C. Hu
 */
@Controller
@SessionAttributes("user")
class HomeController : BaseController() {
    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 内容服务
     */
    @Autowired
    private val articleService: ArticleService? = null

    /**
     * 团体组织服务
     */
    @Autowired
    private val communityService: CommunityService? = null

    /**
     * 海报服务
     */
    @Autowired
    private val posterService: PosterService? = null

    /**
     * 首页
     * @param s: 排序分类条件
     * @param request: 请求参数
     */
    @GetMapping("/")
    fun index(@RequestParam(required = false) s: Int?,
              model: Model, request: HttpServletRequest): String {

        var user = this.sessionUser
        if (user != null) {
            model.addAttribute("user", user)
//            var communityUser = communityService!!.getCommunityAdminUser(user.id)
//            request.session.setAttribute("COMMUNITY_USER_CONTEXT", communityUser)
        }

        //公告通知
        var inap = articleService!!.getArticlesLimited(1).sortDesc("publish_time")
        //赛事新闻
        var inapItems = articleService!!.getArticlesLimited(2).sortDesc("publish_time")
        //运动指南
        var ieap = articleService!!.getArticlesLimited(3).sortDesc("publish_time")

        model.addAttribute("articles", inap)
        model.addAttribute("articles_news", inapItems)
        model.addAttribute("articles_exercise", ieap)

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

        //获取海报信息集合
        var posterItems = posterService!!.getPosterItems()
        for (r in posterItems) {
            if (r.get("avatar") != null) {
                r.setValue(r.fieldsRow().field("avatar", String::class.java), commonService!!.getDownloadUrl(r.get("avatar").toString()))
            }
            if(r.get("activity_id") != null && r.get("activity_id") != 0 ){
                r.setValue(r.fieldsRow().field("link",String::class.java), "/pub/activity/detail/"+r.get("activity_id"))
            }
        }

        model.addAttribute("posterItems", posterItems)

        model.addAttribute("s", if (s == 2) 2 else 1)
        model.addAttribute("activities", activities)

        return "index"
    }

    /**
     * 异步刷新首页Community部分数据
     * @param id: 团体组织ID
     */
    @RequestMapping(value = "/changeCommunity", method = [RequestMethod.POST])
    @ResponseBody
    fun changeCommunity(@RequestParam(required = false) id: Int
                        , request: HttpServletRequest): Boolean {
        var community = communityService!!.getCommunity(id)
        this.sessionCommunity = community

        //取得当前用户登录后的角色
        var user = sessionUser
        if (user != null) {
            //删除COMMUNITY_USER_CONTEXT
            request.session.removeAttribute("COMMUNITY_USER_CONTEXT")
            var communityUser = communityService!!.getCommunityUser(user!!.id, this.sessionCommunity.id)
            if (communityUser != null) {
                request.session.setAttribute("COMMUNITY_USER_CONTEXT", communityUser)
            }
        }

        return true
    }
}