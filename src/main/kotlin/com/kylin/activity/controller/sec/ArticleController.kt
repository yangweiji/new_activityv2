package com.kylin.activity.controller.sec

import com.kylin.activity.databases.tables.daos.ArticleDao
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService

import org.jooq.DSLContext
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

data class ArticlesData(
   var article:Article?=null
)

@Controller
@RequestMapping("sec/article")
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
     * 查询文章内容
     */
    @CrossOrigin
    @RequestMapping(value = "articles", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun allArticle(): String {
        return "sec/article/articles"
    }

    /**
     * 异步查询文章内容
     */
    @CrossOrigin
    @RequestMapping(value = "/getArticles", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun getArticles(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var title = map["title"]
        var category = map["category"]
        var items = articleService!!.getAllArticleUserItem(title, category)
        var list = items.intoMaps()
        return list
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
     * 编辑或新增内容
     */
    @RequestMapping(value="/updateOraddarticle",method = arrayOf(RequestMethod.GET))
    fun updateOraddArticle(@RequestParam(required = false)id:Int?,model: Model):String{
        var articlesData=ArticlesData()
        if(id!=null&&id>0){
            articlesData.article=articleService!!.getArticle(id)
        }else{
            articlesData.article= Article()
        }
        model.addAttribute("articlesData",articlesData)
        return "sec/article/updateOraddarticle"
    }

    /**
     * 保存添加或编辑内容信息
     */
    @PostMapping("/updateOraddarticle")
    fun saveArticle(@ModelAttribute("article")article: Article):String{
         if(article.id!=null&&article.id>0){
             articleDao!!.update(article)
         }else{
             articleDao!!.insert(article)
         }
        return "redirect:/sec/article/articles"
    }
}