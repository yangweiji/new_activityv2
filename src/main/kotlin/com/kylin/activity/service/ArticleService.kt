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
            sql += "and a.title like '%?%' ".replace("?", title!!)
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

    /**
     * 公告通知查询界面
     */
    fun noticeItems(category: Int): Result<Record> {
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit from article a where 1=1 "
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
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit from article a where 1=1 "
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
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit from article a where 1=1 "
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
        var sql = "select a.id,a.category,a.avatar,a.title,a.publish_time,a.unit,a.body from article a where a.id=? "
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
}