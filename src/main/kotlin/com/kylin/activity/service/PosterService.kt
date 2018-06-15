package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.PosterDao
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

    /**
     * 数据访问
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 取得活动对应的唯一海报
     */
    fun getActivityPoseter(activityId: Int?): Poster? {
        var list = posterDao!!.fetchByActivityId(activityId)
        return if (list != null && list.size > 0) list.first() else null
    }

    /**
     * 添加海报信息
     * @param poster: 海报信息
     * @return 活动id
     */
    fun insert(poster: Poster): Int {
        posterDao!!.insert(poster)
        return poster.activityId
    }

    /**
     * 更新海报信息
     * @param poster: 海报信息
     * @return 活动id
     */
    fun update(poster: Poster): Int {
        posterDao!!.update(poster)
        return poster.activityId
    }

   /* fun getActivityPhotoPictureItems(photoId:Int?):Result<Record>{
        var sql="select t1.* from activity_photo_picture t1 inner join activity_photo t2 on t1.activity_photo_id=t2.id " +
                "where t1.activity_photo_id=? "
        return create!!.resultQuery(sql,photoId).fetch()
    }*/

    fun getPosterItems(communityId:Int?):Result<Record>{
        var sql="select t1.* from poster t1 left join activity t2 on t1.activity_id=t2.id where 1=1  " +
                "and t2.community_id=? "
        return create!!.resultQuery(sql,communityId).fetch()
    }
}