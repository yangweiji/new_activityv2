package com.kylin.activity.controller.pub

import com.kylin.activity.databases.Tables
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
@Controller
@RequestMapping("/pub/article")
class IndexCategoryController {
    /**
     * 内容服务
     */
    @Autowired
    private val articleService: ArticleService? = null

    /**
     * 公共服务
     */
    @Autowired
    private  val commonService:CommonService?=null


    /**
     * 内容分类按钮点击操作
     * @param category 内容类型
     * @return 分类信息
     */
    @CrossOrigin
    @RequestMapping(value = "/indexArticleCategory/{category}", method = [RequestMethod.GET])
    fun indexArticleCategory(@PathVariable category: Int?, model: Model): String {
        var articleItems = articleService!!.getArticles(category!!)
        for (r in articleItems) {
            if (r.get("avatar") != null) {
                r.setValue(r.fieldsRow().field("avatar", String::class.java), commonService!!.getDownloadUrl(r.get("avatar").toString()))
            }
        }
        model.addAttribute("category",category)
        model.addAttribute("article", articleItems)
        return "pub/article/indexArticleCategory"
    }


    /**
     * 内容详情信息
     * @param id 内容编号
     * @return 详情信息
     */
    @GetMapping("/indexArticleDetail/{id}")
    fun noticeDetail(@PathVariable("id") id: Int, model: Model): String {
        var article = articleService!!.getCategoryDetail(id)
        if (article["avatar"] != null)
        {
            article.setValue(Tables.ARTICLE.AVATAR, commonService!!.getDownloadUrl(article.get("avatar",String::class.java)))
        }
        model.addAttribute("article", article)
        return "pub/article/indexArticleDetail"
    }




    /**
     * 内容列表信息
     * @param id 内容编号
     * @return 列表信息
     */
    @GetMapping("/indexArticleList/{id}")
    fun noticeList(@PathVariable("id")id:Int,model: Model):String{
        var articles=articleService!!.getCategoryDetail(id)
        if(articles["avatar"]!=null){
            articles.setValue(Tables.ARTICLE.AVATAR,commonService!!.getDownloadUrl(articles.get("avatar", String::class.java)))
        }
        model.addAttribute("articles",articles)
        return "pub/article/indexArticleList"
    }


}