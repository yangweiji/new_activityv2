package com.kylin.activity.controller.pub

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.Article
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService
import javafx.scene.control.Tab
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.websocket.server.PathParam

@Controller
@RequestMapping("/pub/article")
class IndexCategoryController {
    @Autowired
    private val articleService: ArticleService? = null

    @Autowired
    private  val commonService:CommonService?=null


    /**
     * 公告通知查询界面
     */
    @CrossOrigin
    @RequestMapping(value = "/indexNotice/{category}", method = [RequestMethod.GET])
    fun noticeSearch(@PathVariable category: Int?, model: Model): String {
        var noticeItems = articleService!!.getArticles(category!!)
        for (r in noticeItems) {
            if (r.get("avatar") != null) {
                r.setValue(r.fieldsRow().field("avatar", String::class.java), commonService!!.getDownloadUrl(r.get("avatar").toString()))
            }
        }
        model.addAttribute("category",category)
        model.addAttribute("article", noticeItems)
        return "pub/article/indexNotice"
    }


    @GetMapping("/noticeDetail/{id}")
    fun noticeDetail(@PathVariable("id") id: Int, model: Model): String {
        var article = articleService!!.getCategoryDetail(id)
        if (article["avatar"] != null)
        {
            article.setValue(Tables.ARTICLE.AVATAR, commonService!!.getDownloadUrl(article.get("avatar",String::class.java)))
        }
        model.addAttribute("article", article)
        return "pub/article/noticeDetail"
    }


    /**
     * 赛事新闻查询界面
     */
    @CrossOrigin
    @RequestMapping(value = "/indexNews/{category}", method = [RequestMethod.GET])
    fun newsSearch(@PathVariable category: Int?, model: Model): String {
        var newsItems = articleService!!.getArticles(category!!)
        for(r in newsItems){
            if(r.get("avatar")!=null){
                r.setValue(r.fieldsRow().field("avatar",String::class.java),commonService!!.getDownloadUrl(r.get("avatar").toString()))
            }
        }
        model.addAttribute("category",category)
        model.addAttribute("article", newsItems)
        return "pub/article/indexNews"
    }


    @GetMapping("/newsDetail/{id}")
    fun newsDetail(@PathVariable("id") id: Int, model: Model): String {
        var article = articleService!!.getCategoryDetail(id)
        if (article["avatar"] != null)
        {
            article.setValue(Tables.ARTICLE.AVATAR, commonService!!.getDownloadUrl(article.get("avatar",String::class.java)))
        }
        model.addAttribute("article", article)
        return "pub/article/newsDetail"
    }


    /**
     * 运动指南查询界面
     */
    @CrossOrigin
    @RequestMapping(value = "/indexExercise/{category}", method = [RequestMethod.GET])
    fun exerciseSearch(@PathVariable category: Int?, model: Model): String {
        var newsItems = articleService!!.getArticles(category!!)
        for (r in newsItems){
            r.setValue(r.fieldsRow().field("avatar",String::class.java),commonService!!.getDownloadUrl(r.get("avatar").toString()))
        }
        model.addAttribute("category",category)
        model.addAttribute("article", newsItems)
        return "pub/article/indexExercise"
    }


    @GetMapping("/exerciseDetail/{id}")
    fun exerciseDetail(@PathVariable("id") id: Int, model: Model): String {
        var article = articleService!!.getCategoryDetail(id)
        if (article["avatar"] != null)
        {
            article.setValue(Tables.ARTICLE.AVATAR, commonService!!.getDownloadUrl(article.get("avatar",String::class.java)))
        }
        model.addAttribute("article", article)
        return "pub/article/exerciseDetail"
    }

    /**
     * 公告通知列表信息
     */
    @GetMapping("/noticeList/{id}")
    fun noticeList(@PathVariable("id")id:Int,model: Model):String{
        var articles=articleService!!.getCategoryDetail(id)
        if(articles["avatar"]!=null){
            articles.setValue(Tables.ARTICLE.AVATAR,commonService!!.getDownloadUrl(articles.get("avatar", String::class.java)))
        }
        model.addAttribute("articles",articles)
        return "pub/article/noticeList"
    }

    /**
     * 赛事新闻列表信息
     */
    @GetMapping("/newsList/{id}")
    fun newsList(@PathVariable("id")id: Int,model:Model):String{
        var articles=articleService!!.getCategoryDetail(id)
        if(articles["avatar"]!=null){
            articles.setValue(Tables.ARTICLE.AVATAR,commonService!!.getDownloadUrl(articles.get("avatar",String::class.java)))
        }
        model.addAttribute("articles",articles)
        return "pub/article/newsList"
    }

    /**
     * 运动指南列表信息
     */
    @GetMapping("/exerciseList/{id}")
    fun exerciseList(@PathVariable("id")id:Int,model:Model):String{
       var articles=articleService!!.getCategoryDetail(id)
        if(articles["avatar"]!=null){
           articles.setValue(Tables.ARTICLE.AVATAR,commonService!!.getDownloadUrl((articles.get("avatar",String::class.java))))
        }
        model.addAttribute("articles",articles)
        return "pub/article/exerciseList"
    }
}