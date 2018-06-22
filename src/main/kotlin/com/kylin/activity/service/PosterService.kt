package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.PosterDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.Poster
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: weiji.yang
 * @Date: 2018/6/15 12:20
 * @Description: 海报服务层
 */
@Service
class PosterService {

    /**
     * 海报DAO
     */
    @Autowired
    private val posterDao: PosterDao? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 数据访问
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 添加海报信息
     * @param poster: 海报信息
     */
    fun insertPoster(poster: Poster) {
        posterDao!!.insert(poster)
    }

    /**
     * 更新海报信息
     * @param title: 海报信息
     */
    fun updatePoster(title: String, avatar: String, mobileAvatar: String, link: String,
                     activityId: Int, posterType: String, show: Boolean, sequence: Int, id: Int): Any {
        var sql = "update poster SET title=?,avatar=?,mobile_avatar=?,link=?,activity_id=?, " +
                "poster_type=?,`show`=?,sequence=? WHERE id=? "
        return create!!.execute(sql, title, avatar, mobileAvatar, link, activityId, posterType, show, sequence, id)
    }


    /**
     * 获取海报信息集合，
     * 排序相同时再通过时间倒序排
     */
    fun getPosterItems(): Result<Record> {
        var sql = "select t1.*,t2.body from poster t1 LEFT JOIN activity t2 on t1.activity_id=t2.id where 1=1 " +
                "and t1.`show`=1 order by t1.sequence asc,t1.created desc limit 6 "
        return create!!.resultQuery(sql).fetch()
    }


    /**
     * 获取海报信息集合
     * @param title 海报标题
     * @param posterType 海报类型
     * @return 海报信息集合
     */
    fun queryPosterItems(title: String?, posterType: String?): Result<Record> {
        var sql = "select t1.*,t2.tags from poster t1 " +
                "left join activity t2 on t1.activity_id=t2.id where 1=1 "
        var params = mutableListOf<Any?>()
        if (!title.isNullOrBlank()) {
            sql += "and t1.title like ? "
            params.add("%$title%")
        }
        if (!posterType.isNullOrBlank() && posterType != "0") {
            sql += "and t1.poster_type = ? "
            params.add(posterType)
        }

        sql += "order by t1.sequence asc,t1.created desc "
        return create!!.resultQuery(sql, *params.toTypedArray()).fetch()
    }

    /**
     * 根据id,取得海报信息
     * @param id 海报id
     * @return 海报信息
     */
    fun getPoster(id: Int?): Poster {
        return posterDao!!.findById(id)
    }

    /**
     * 获取单个海报信息
     * @param title 海报标题
     */
    fun getPosterTitle(title: String): Poster? {
        return posterDao!!.fetchOne(Tables.POSTER.TITLE, title)
    }


    fun getPosterByActivityId(activityId: Int):Poster{
       return posterDao!!.fetchOne(Tables.POSTER.ACTIVITY_ID,activityId)
    }

    fun getActivity(activityId: Int?): Activity {
        return activityDao!!.findById(activityId)
    }

    /**
     * 删除海报信息
     * @param id 海报id
     */
    fun deletePoster(id: Int) {
        posterDao!!.deleteById(id)
    }

    /**
     * 隐藏海报
     * @param id 海报id
     */
    fun showPoster(id: Int): Record {
        var sql = "UPDATE poster set `show`=0 where id=? "
        return create!!.resultQuery(sql, id).fetchOne()
    }

    /**
     * 显示海报
     * @param id 海报id
     */
    fun displayPoster(id: Int): Record {
        var sql = "UPDATE poster set `show`=1 where id=? "
        return create!!.resultQuery(sql, id).fetchOne()
    }


    /**
     * 微信端获取海报详情信息
     * @param activityId 活动id
     */
    fun getPosterDetail(activityId: Int?): Record {
        var sql = "select t1.mobile_avatar,t1.activity_id, t2.* from poster t1 LEFT JOIN activity t2 on t1.activity_id=t2.id where t1.activity_id=? "
        return create!!.resultQuery(sql, activityId).fetchOne()
    }

    /**
     * 微信端获取海报信息
     */
    fun getPosterByActivityId(): Result<Record> {
        var sql = "select t1.* from poster t1 LEFT JOIN activity t2 on t1.activity_id=t2.id where 1=1  " +
                "and t1.`show`=1 order by t1.sequence asc,t1.created desc limit 6 "
        return create!!.resultQuery(sql).fetch()
    }

    /**
     * 获取全部海报信息
     * @param posterType 海报类型
     */
    fun getAllPoster(posterType: String): Result<Record> {
        var sql = "select t1.* from poster t1 left join " +
                "activity t2 on t1.activity_id=t2.id where t1.poster_type=? " +
                "order by t1.sequence asc,t1.created desc limit 50 "
        return create!!.resultQuery(sql, posterType).fetch()
    }

    fun getPosterLinkDetail(activityId: Int?): Record {
        var sql ="select t1.* from poster t1 left join activity t2 " +
                "on t1.activity_id=t2.id where 1=1 and t1.activity_id=?  "
        return create!!.resultQuery(sql,activityId).fetchOne()
    }


   /* fun getPosterActivityId(): Poster? {
        var list=posterDao!!.fetchByActivityId()
        return if (list != null && list.size > 0) list.first() else null
    }
*/
    fun getPosterActivityId():Result<Record>{
       var sql="select t1.* from poster t1 left join activity t2 on t1.activity_id=t2.id "
       return create!!.resultQuery(sql).fetch()
}

}