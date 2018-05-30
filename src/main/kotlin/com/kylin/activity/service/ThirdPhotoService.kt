package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThirdPhotoService {
    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null

    /**
     * 按相册标题查询相册信息
     * @param description: 相册描述
     * @param cid: 组织ID
     */
    fun getAllPhotosItems(description: String?, cid: Int): Result<Record> {
        var sql = "select a.* from activity_photo a " +
                "left join activity a2 on a.activity_id=a2.id " +
                "where 1=1 and ?=a2.community_id "
        var params = mutableListOf<Any?>()
        if (!description.isNullOrBlank()) {
            sql += "and a.description like '%?%' ".replace("?", description!!)
            params.add(description)
        }

        return create!!.resultQuery(sql, cid, params.toTypedArray()).fetch()
    }

    /**
     * 编辑活动相册
     */
    fun getAllPhotoId(id: Int?): ActivityPhoto? {
        return activityPhotoDao!!.findById(id)
    }
}