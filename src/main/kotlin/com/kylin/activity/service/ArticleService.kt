package com.kylin.activity.service

import com.kylin.activity.databases.Tables
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
    private val articleDao: ArticleDao? = null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 如不按指定的条件查询，则显示全部内容
     * 按内容的标题分类查询内容
     * 按内容的分类查询内容
     */
    fun getAllArticleUserItem(title: String?, category: String?): Result<Record> {
        var sql = "select a.* from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!title.isNullOrBlank()) {
            sql += "and a.title like '%?%' ".replace("?", title!!)
            params.add(title)
        }
        if (!category.isNullOrBlank() && category != "0") {
            sql += "and a.category = ? "
            params.add(category)
        }
        return create!!.resultQuery(sql, * params.toTypedArray()).fetch()

    }

    /**
     * 取得内容的信息
     */
    fun getArticle(id: Int?): Article {
        var article = articleDao!!.findById(id)
        return article
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
    fun deleteArticleById(id: Int) {
        articleDao!!.deleteById(id)
    }

    /**
     * 公告通知查询界面
     */
    fun noticeItems(category: Int): Result<Record> {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.summary from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (category != null) {
            sql += "and a.category = 1 "
            params.add(category)
        }
        sql += "order by a.publish_time desc"
        return create!!.resultQuery(sql, params.toTypedArray()).fetch()

    }

    /**
     * 赛事新闻查询界面
     */
    fun newsItems(category: Int): Result<Record> {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.summary from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (category != null) {
            sql += "and a.category = 2 "
            params.add(category)
        }
        sql += "order by a.publish_time desc"
        return create!!.resultQuery(sql, * params.toTypedArray()).fetch()

    }

    /**
     * 运动指南查询界面
     */
    fun exerciseItems(category: Int): Result<Record> {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.summary from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (category != null) {
            sql += "and a.category = 3 "
            params.add(category)
        }
        sql += "order by a.publish_time desc"
        return create!!.resultQuery(sql, * params.toTypedArray()).fetch()

    }

    /**
     * 取得分类详情信息
     */
    fun getCategoryDetail(id: Int): Record {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.summary,a.body from article a where a.id=? "
        var notice = create!!.resultQuery(sql, id).fetchOne()
        return notice
    }

    /**
     * 公告通知列表信息
     */
    fun indexNoticeAndPublishTime(category: String?): Result<Record> {
        var sql = "select a.id, a.category, a.title , a.publish_time from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!category.isNullOrBlank()) {
            sql += "and a.category = 1 "
            params.add(category)
        }
        sql += "order by a.publish_time desc limit 3"
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()
    }

    /**
     * 赛事新闻列表信息
     */
    fun indexNewsAndPublishTime(category: String?): Result<Record> {
        var sql = "select a.id, a.category, a.title , a.publish_time from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!category.isNullOrBlank()) {
            sql += "and a.category = 2 "
            params.add(category)
        }
        sql += "order by a.publish_time desc limit 3"
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()
    }

    /**
     * 运动指南列表信息
     */
    fun indexExerciseAndPublishTime(category: String?): Result<Record> {
        var sql = "select a.id, a.category, a.title , a.publish_time from article a where 1=1 "
        var params = mutableListOf<Any?>()
        if (!category.isNullOrBlank()) {
            sql += "and a.category =3 "
            params.add(category)
        }
        sql += "order by a.publish_time desc limit 3"
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()
    }

    /**
     * 添加内容
     * @param article 文章
     */
    fun insertArticle(article: Article) {
        articleDao!!.insert(article)
    }

    /**
     * 更新内容
     * @param article 文章
     */
    fun updateArticle(article: Article) {
        articleDao!!.update(article)
    }

    fun getStatus(article: Article): Int {
        articleDao!!.fetchByStatus(article.status)
        return article.status
    }


     /**
     * 取得用户信息
     */
    fun getArticleTitle(title:String):Article?{
         var title=articleDao!!.fetchOne(Tables.ARTICLE.TITLE,title)
         return title
     }
   /* fun getUser(username: String): User? {
        var user = userDao!!.fetchOne(Tables.USER.USERNAME, username)
        return user
    }*/
}