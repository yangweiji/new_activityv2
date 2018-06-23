package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.ArticleDao
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil

import org.jooq.DSLContext
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

data class ArticlesData(
        var article: Article? = null
)

@Controller
@RequestMapping("sec/admin/article")
class ArticleController : BaseController() {
    /**
     * 内容服务
     */
    @Autowired
    private val articleService: ArticleService? = null

    /**
     * 查询文章内容
     */
    @CrossOrigin
    @RequestMapping(value = "articles", method = [RequestMethod.GET, RequestMethod.POST])
    fun allArticle(): String {
        return "sec/admin/article/articles"
    }

    /**
     * 异步查询文章内容
     * @param map 文章内容集合
     */
    @CrossOrigin
    @RequestMapping(value = "/getArticles", method = [RequestMethod.GET, RequestMethod.POST])
    @ResponseBody
    fun getArticles(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var title = map["title"]
        var category = map["category"]
        var items = articleService!!.getAllArticleUserItem(title, category)
        return items.intoMaps()
    }

    /**
     * 删除内容的信息
     * articleId 内容编号
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteArticle", method = [RequestMethod.POST])
    @ResponseBody
    fun deleteArticle(request: HttpServletRequest): Any? {
        var articleId = if (request.getParameter("articleId") != null)
            request.getParameter("articleId").toInt() else 0
        articleService!!.deleteArticleById(articleId)
        return true
    }

    /**
     * 编辑或新增内容
     * @param id 内容编号
     * @param model 存放内容信息数据模型
     */
    @RequestMapping(value = "/article", method = [RequestMethod.GET])
    fun article(@RequestParam(required = false) id: Int?, model: Model, request: HttpServletRequest): String {
        var articlesData = ArticlesData()
        if (id != null && id > 0) {
            articlesData.article = articleService!!.getArticle(id)
        } else {
            articlesData.article = Article()
        }
        model.addAttribute("articlesData", articlesData)
        return "sec/admin/article/article"
    }

    /**
     * 保存添加或编辑内容信息
     * @param article 内容
     * @return
     */
    @PostMapping("/saveArticle")
    fun saveArticle(@ModelAttribute("article") article: Article?,
                    model: Model, redirectAttributes: RedirectAttributes): String {
        var user = this.sessionUser
        if (article!!.id != null && article.id > 0) {
            var title = article.title
            var summary = article.summary
            var avatar = article.avatar
            var unit = article.unit
            var body = article.body
            var status = article.status
            var publishTime = article.publishTime
            var category = article.category
            article.modified = DateUtil.date().toTimestamp()
            article.modifiedBy = user!!.id
            //禁止状态，发布时间改成：1970-01-01
            if (article.status == -1) {
                category = 4
                var year = 1970
                var month = 1
                var vardayOfMonth = 1
                var hour = 0
                var minute = 0
                var second = 0
                var nanoOfSecond = 0
                publishTime = Timestamp.valueOf(LocalDateTime.of(year, month, vardayOfMonth, hour, minute, second, nanoOfSecond))
                redirectAttributes.addFlashAttribute("errorMessage", "文章【${title}】已被禁用！")
                articleService!!.updateArticle(title, summary, avatar, unit, body, status,
                        article.modified, article.modifiedBy, publishTime, category, article.id)
                return "redirect:/sec/admin/article/articles"
            }
            //发布状态，获得系统当前时间
            if (article.status == 1) {
                publishTime = DateUtil.date().toTimestamp()
                redirectAttributes.addFlashAttribute("globalMessage", "文章【${title}】发布成功！")
            }
            articleService!!.updateArticle(title, summary, avatar, unit, body, status,
                    article.modified, article.modifiedBy, publishTime, category, article.id)
        } else {
            var articleTitle = articleService!!.getArticleTitle(article.title)
            if (articleTitle != null) {
                return "文章【${article.title}】已存在"
            }
            var articles = Article()
            articles.title = article.title
            articles.createdBy = user!!.id
            articles.created = DateUtil.date().toTimestamp()
            articles.publishTime = article.publishTime
            articles.status = article.status
            articles.category = article.category
            articles.avatar = article.avatar
            articles.body = article.body
            //0：默认为平台发布
            articles.communityId = 0
            articles.summary = article.summary
            articles.unit = article.unit
            //草稿状态
            if (article.status == 0) {
                redirectAttributes.addFlashAttribute("globalMessage", "文章【${article.title}】添加成功！")
                articleService!!.insertArticle(articles)
                return "redirect:/sec/admin/article/articles"
            }
        }
        return "redirect:/sec/admin/article/articles"
    }
}