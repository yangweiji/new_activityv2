package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.service.ArticleService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

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
        var article: Article
        if (id != null && id > 0) {
            article = articleService!!.getArticle(id)
        } else {
            article = Article()
            article.status = 1  //默认设置为发布状态
            article.category = 1    //默认设置为公告通知
            article.created = DateUtil.date().toTimestamp()
        }
        model.addAttribute("article", article)
        return "sec/admin/article/article"
    }

    /**
     * 保存添加或编辑内容信息
     * @param article 内容
     * @return
     */
    @PostMapping("/saveArticle")
    fun saveArticle(@ModelAttribute("article") article: Article,
                    model: Model, redirectAttributes: RedirectAttributes): String {
        var user = this.sessionUser
        if (article.id != null) {

            article.modified = DateUtil.date().toTimestamp()
            article.modifiedBy = user!!.id
            articleService!!.update(article)
        } else {
            if (articleService!!.getArticleTitle(article.title) != null) {
                model.addAttribute("errorMessage", "文章【${article.title}】已存在！")
                return "/sec/admin/article/article"
            }

            article.created = DateUtil.date().toTimestamp()
            article.createdBy = user!!.id
            redirectAttributes.addFlashAttribute("globalMessage", "文章【${article.title}】添加成功！")
            articleService!!.insertArticle(article)
        }

        return "redirect:/sec/admin/article/articles"
    }
}