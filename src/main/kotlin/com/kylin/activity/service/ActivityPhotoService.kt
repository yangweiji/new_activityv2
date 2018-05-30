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
import org.springframework.transaction.annotation.Transactional

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
    var page: Int = 0

    /**
     * 定义每页记录数
     */
    var size: Int = 12

    /**
     * 取得活动相册总数
     */
    var activityphotoCount: Long = 0

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
     * 添加相册信息
     * @param activityPhoto: 相册信息
     * @return 相册ID
     */
    fun insert(activityPhoto: ActivityPhoto): Int {
        activityPhotoDao!!.insert(activityPhoto)
        return activityPhoto.id
    }


    /**
     * 更新相册信息
     * @param activityPhoto: 相册信息
     * @return 相册ID
     */
    fun update(activityPhoto: ActivityPhoto): Int {
        activityPhotoDao!!.update(activityPhoto)
        return activityPhoto.id
    }

    /***
     * 删除相册
     * @param activityPhotoId: 相册ID
     */
    @Transactional
    fun delete(activityPhotoId: Int?) {
        //删除相册下的所有图片
        create!!.execute("delete from activity_photo_picture where activity_photo_id = ?", activityPhotoId)
        //删除相册
        activityPhotoDao!!.deleteById(activityPhotoId)
    }

    /**
     * 添加相册图片
     * @param activityPhotoPicture: 图片信息
     * @return 图片ID
     */
    fun insertPicture(activityPhotoPicture: ActivityPhotoPicture): Int {
        activityPhotoPictureDao!!.insert(activityPhotoPicture)
        return activityPhotoPicture.id
    }

    /**
     * 更新相册图片信息
     * @param activityPhotoPicture: 图片信息
     * @return 图片ID
     */
    fun updatePicture(activityPhotoPicture: ActivityPhotoPicture): Int {
        activityPhotoPictureDao!!.update(activityPhotoPicture)
        return activityPhotoPicture.id
    }


    /***
     * 删除相册图片
     * @param pictureId: 相册图片ID
     */
    fun deletePicture(pictureId: Int) {
        activityPhotoPictureDao!!.deleteById(pictureId)
    }


    /**
     * 获取所有的图片信息
     * @param activityPhotoId： 相册ID
     * @return 相册下的图片集合
     */
    fun getPhotoPictureList(activityPhotoId: Int?): List<ActivityPhotoPicture> {
        return activityPhotoPictureDao!!.fetchByActivityPhotoId(activityPhotoId)
    }

    /**
     * 取得活动对应的唯一相册
     * @param activityId: 活动ID
     * @return 活动对应的首个相册
     */
    fun getFirstActivityPhoto(activityId: Int?): ActivityPhoto? {
        var list = activityPhotoDao!!.fetchByActivityId(activityId)
        return if (list != null && list.size > 0) list.first() else null
    }


}