package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.ArticleDao
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.util.CommonService
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.*

@Service
class ArticleService {
    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val articleDao: ArticleDao? = null

    @Autowired
    private val create: DSLContext? = null


    /**
     * 如不按指定的条件查询，则显示全部内容
     * 按内容的标题分类查询内容
     * 按内容的分类查询内容
     */
    fun getAllArticleUserItem(title: String?, category: String?): Result<Record> {
        var sql = "select a.id,a.title,a.publish_time,a.category,a.status,a.community_id from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!title.isNullOrBlank()) {
            sql += "and a.title like '%?%' ".replace("?",title!!)
            params.add(title)
        }
        if (!category.isNullOrBlank()) {
            sql += "and a.category = ? "
            params.add(category)
        }
        /*sql += "order by a.publish_time"*/
        return create!!.resultQuery(sql, * params.toTypedArray()).fetch()

    }

    /**
     * 取得内容的信息
     */
    fun getArticle(id: Int): Article {
        var article = articleDao!!.findById(id)
        return article
    }

    /**
     * 删除内容
     */
    fun deleteArticleById(id: Int) {
        articleDao!!.deleteById(id)
    }

    /**
     * 添加内容
     */
    fun createArticle(articleItem: Article): Article {
        articleDao!!.insert(articleItem)
        return articleItem
    }

    /**
     * 更新内容
     */
    fun articleUpdate(article: Article) {
        articleDao!!.update(article)
    }
}