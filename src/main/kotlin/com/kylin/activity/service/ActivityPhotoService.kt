package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoPictureDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.databases.tables.pojos.ActivityPhotoPicture
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityPhotoService {
    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null

    @Autowired
    private val activityPhotoPictureDao: ActivityPhotoPictureDao? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 定义分页索引起始位置
     */
    public var page: Int = 0

    /**
     * 定义每页记录数
     */
    public var size: Int = 12

    /**
     * 取得活动相册总数
     */
    public var activityphotoCount: Long = 0

    fun getActivityPhoto(id: Int): Result<Record> {
        var sql = "select a.* from activity_photo a where ?=a.activity_id "
        return create!!.resultQuery(sql, id).fetch()
    }

    fun getActivityPhotoDetail(id: Int): Result<Record> {
        var sql = "select a.* from activity_photo a where 1=1 and a.id=? "
        return create!!.resultQuery(sql, id).fetch()
    }

    /**
     * 相册分页
     */
    fun getPhotoPage(): Result<Record> {
        var sql_count = "select count(*) count from activity_photo where 1=1 {0} {1} "
        var sql = "select a.* from activity_photo a where 1=1 " +
                "order by a.created desc limit {99},{100} "

        activityphotoCount = create!!.resultQuery(sql_count).fetch("count")[0] as Long

        //分页条件
        if (page * size < 0) {
            sql = sql.replace("{99}", "0")
        } else {
            sql = sql.replace("{99}", (page * size).toString())
        }
        sql = sql.replace("{100}", size.toString())
        return create!!.resultQuery(sql).fetch()
    }


    /**
     * 查询相册信息
     */
    /*fun getActivityPhotos(id: Int?): Result<Record> {
        var photoCountSql = "select count(*) from activity_photo where activity_id=? "
        return create!!.resultQuery(photoCountSql, id).fetch()
    }*/

    /**
     * 添加相册信息
     */
    fun insertActivityPhoto(activityPhoto: ActivityPhoto): Int {
        var sql = "insert into activity_photo(activity_id, picture, description, created, created_by, axtenal_url) " +
                "values(?,?,?,?,?,?)"
        return create!!.execute(sql, activityPhoto.activityId, activityPhoto.picture, activityPhoto.description, activityPhoto.created, activityPhoto.createdBy, activityPhoto.axtenalUrl)
    }


    /**
     * 获取所有的图片信息
     */
    fun getPhotoPicture(activityPhotoId: Int?): List<ActivityPhotoPicture> {
        return activityPhotoPictureDao!!.fetchByActivityPhotoId(activityPhotoId)
//        var pictureSql = "select * from activity_photo_picture where activity_photo_id=? "
//        return create!!.resultQuery(pictureSql, activityPhotoId).fetch()
    }

    /**
     * 取得活动对应的唯一相册
     */
    fun getOnlyOneActivityPhoto(activityId: Int?): ActivityPhoto {
        var list = activityPhotoDao!!.fetchByActivityId(activityId)
        return list[0]
    }


}