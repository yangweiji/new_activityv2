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
import javax.servlet.http.HttpServletRequest

data class ArticlesData(
        var article: Article? = null
)

@Controller
@RequestMapping("sec/article")
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
    @RequestMapping(value = "articles", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun allArticle(): String {
        return "sec/article/articles"
    }

    /**
     * 异步查询文章内容
     * @param map 文章内容集合
     */
    @CrossOrigin
    @RequestMapping(value = "/getArticles", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun getArticles(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var title = map["title"]
        var category = map["category"]
        var items = articleService!!.getAllArticleUserItem(title, category)
        return items.intoMaps()
    }

    /**
     * 删除内容的信息
     * @param id 内容编号
     * @param model 存放内容信息数据模型
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteArticle/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteArticle(@PathVariable id: Int, model: Model): String {
        articleService!!.deleteArticleById(id)
        return "redirect:/sec/article/articles"
    }

    /**
     * 编辑或新增内容
     * @param id 内容编号
     * @param model 存放内容信息数据模型
     */
    @RequestMapping(value = "/updateOraddarticle", method = arrayOf(RequestMethod.GET))
    fun updateOraddArticle(@RequestParam(required = false) id: Int?, model: Model): String {
        var articlesData = ArticlesData()
        if (id != null && id > 0) {
            articlesData.article = articleService!!.getArticle(id)
        } else {
            articlesData.article = Article()
        }
        model.addAttribute("articlesData", articlesData)
        return "sec/article/updateOraddarticle"
    }

    /**
     * 保存添加或编辑内容信息
     * @param article 内容
     */
    @PostMapping("/updateOraddarticle")
    fun saveArticle(@ModelAttribute("article") article: Article?, @RequestParam("status") status: Int): String {
        var user = this.sessionUser

        if (article!!.id != null && article.id > 0) {
            article!!.modified = DateUtil.date().toTimestamp()
            article!!.modifiedBy = user!!.id
            article!!.createdBy = user!!.id
            article!!.status = articleService!!.getStatus(article)

            //禁止状态
            if (article.status == -1) {

            }

            //发布状态
            if (article.status == 1) {
                return "redirect:/"
            }

            articleService!!.updateArticle(article)
        } else {
            var article = article
            article = articleService!!.getArticleTitle(article.title)
            if (article!!.title != null) {
                throw Exception("内容已存在!")
            } else {
                article!!.createdBy = user!!.id
                article!!.created = DateUtil.date().toTimestamp()
                article!!.modifiedBy = user!!.id
                article!!.status = articleService!!.getStatus(article)

                articleService!!.insertArticle(article)
            }
        }

        return "redirect:/sec/article/articles"
    }
}