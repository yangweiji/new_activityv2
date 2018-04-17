package com.kylin.activity.controller.sec

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ArticleDao
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("sec/article")
@SessionAttributes("article")
class ArticleController {
    @Autowired
    private val articleService: ArticleService? = null

    @Autowired
    private val articleDao: ArticleDao? = null

    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val commonService: CommonService? = null

    /**
     * 新增内容
     */
    @RequestMapping(value = "/pubarticle", method = arrayOf(RequestMethod.GET))
    fun createArticle(model: Model, request: HttpServletRequest): String {
        var article = Article()
        model.addAttribute("article", article)
        return "sec/article/pubarticle"
    }

    /**
     * 保存内容
     */
    @RequestMapping(value = "/saveArticle", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    fun saveArticle(@ModelAttribute("article") article: Article): String {
        var article = article
        articleService!!.createArticle(article)
        return "redirect:/sec/article/articles"
    }


    /**
     * 文章内容
     */
    @CrossOrigin
    @RequestMapping(value = "/articles", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun allArticle(request: HttpServletRequest, model: Model): String {
        var title = request.getParameter("title")
        var category = request.getParameter("category")
        var articleItems = articleService!!.getAllArticleUserItem(title, category)
        model.addAttribute("article", articleItems)
        return "sec/article/articles"
    }

    /**
     * 删除内容的信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteArticle/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteArticle(@PathVariable id: Int, model: Model): String {
        articleService!!.deleteArticleById(id)
        return "redirect:/sec/article/articles"
    }

    /**
     * 更新内容
     */
    @RequestMapping(value = "/article_update/{id}", method = arrayOf(RequestMethod.GET))
    fun articleUpdate(@PathVariable("id") id: Int, model: Model): String {
        var article = articleService!!.getArticle(id)
        model.addAttribute("article", article)
        return "sec/article/article_update"
    }


    @PostMapping("/article_update")
    fun postArticle(article: Article): String {
        articleDao!!.update(article)
        return "redirect:/sec/article/articles"
    }

}