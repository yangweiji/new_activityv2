package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ArticleDao
import com.kylin.activity.databases.tables.pojos.Article
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
@CacheConfig(cacheNames = ["article"])
class ArticleService {
    @Autowired
    private val articleDao: ArticleDao? = null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 分类文章在首页显示的限制数
     */
    val articleLimit: Int = 3

    /**
     * 如不按指定的条件查询，则显示全部内容
     * 按内容的标题分类查询内容
     * 按内容的分类查询内容
     */
    fun getAllArticleUserItem(title: String?, category: String?): Result<Record> {
        var sql = "select a.* from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!title.isNullOrBlank()) {
            sql += "and a.title like ? "
            params.add("%$title%")
        }
        if (!category.isNullOrBlank() && category != "0") {
            sql += "and a.category = ? "
            params.add(category)
        }
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()

    }

    /**
     * 取得内容的信息
     */
    fun getArticle(id: Int?): Article {
        return articleDao!!.findById(id)
    }

    /***
     * 依据文章分类取得文章列表信息
     * @param category: 文章分类
     * @return 文章列表信息
     */
    fun getArticleListByCategory(category: Int?): Result<Record> {
        var sql = "select t1.*, t2.displayname \n" +
                "from article t1 \n" +
                "left join `user` t2 on t1.created_by = t2.id\n" +
                "where t1.category = ?"
        return create!!.resultQuery(sql, category).fetch()
    }

    /**
     * 依据文章ID取得单个文章信息
     * @param id: 文章ID
     * @return 单个文章信息
     */
    fun getArticleExt(id: Int?): Record? {
        var sql = "select t1.*, t2.displayname \n" +
                "from article t1 \n" +
                "left join `user` t2 on t1.created_by = t2.id\n" +
                "where t1.id = ?"
        return create!!.resultQuery(sql, id).fetchOne()
    }


    /**
     * 删除内容
     * @param id  内容编号
     */
    @CacheEvict(allEntries = true)
    fun deleteArticleById(id: Int) {
        articleDao!!.deleteById(id)
    }


    /**
     * 取得分类详情信息
     * @param id 内容编号
     * @return 文章详情信息
     */
    fun getCategoryDetail(id: Int): Record {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.summary,a.body from article a where a.id=? "
        return create!!.resultQuery(sql, id).fetchOne()
    }

    /**
     * 取得文章列表信息,限制记录数
     * 添加缓存机制
     * @param category: 分类
     * @return 文章列表信息
     */
    @Cacheable()
    fun getArticlesLimited(category: Int): Result<Record> {
        var sql = "select a.id, a.category, a.title , a.publish_time from article a where category = ? and a.status=1 " +
                "order by a.publish_time desc limit ?"
        return create!!.resultQuery(sql, category, articleLimit).fetch()
    }

    /**
     * 取得文章列表信息
     * @param category: 分类
     * @return 文章列表信息
     */
    fun getArticles(category: Int): Result<Record> {
        var sql = "select a.* from article a where category = ? " +
                "order by a.publish_time desc"
        return create!!.resultQuery(sql, category).fetch()
    }

    /**
     * 添加内容
     * @param article 文章
     */
    @CacheEvict(allEntries = true)
    fun insertArticle(article: Article) {
        articleDao!!.insert(article)
    }

    /**
     * 更新内容
     * @param article 文章
     */
    @CacheEvict(allEntries = true)
    fun update(article: Article) {
        articleDao!!.update(article)
    }


    /**
     * 更新内容
     * @param article 文章
     */
    @CacheEvict(allEntries = true)
    fun updateArticle(title: String,summary:String,avatar:String,unit:String,body:String,status:Int,
                      modified:Timestamp,modified_by:Int,pulishTime:Timestamp,category: Int,id:Int?):Any {
        var sql="update article set title=?,summary=?,avatar=?,unit=?, " +
                "body=?,status=?,modified=?,modified_by=?,publish_time=?,category=? " +
                "where id=?  "
        return create!!.execute(sql,title,summary,avatar,unit,body,status,modified,modified_by,pulishTime,category,id)
    }


    /**
     * 发布状态
     * @param article 文章
     */
    fun getStatus(article: Article): Int {
        articleDao!!.fetchByStatus(article.status)
        return article.status
    }


    /**
     * 获取单个内容信息
     * @param title 内容标题
     */
    fun getArticleTitle(title: String): Article? {
        return articleDao!!.fetchOne(Tables.ARTICLE.TITLE, title)
    }
}