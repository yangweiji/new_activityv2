package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.*
import com.kylin.activity.databases.tables.pojos.*
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 活动服务
 * @author Richard C. Hu
 */
@Service
class ActivityUserRecordService {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 活动DAO
     */
    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 活动收藏DAO
     */
    @Autowired
    private val activityFavoriteDao: ActivityFavoriteDao? = null

    /**
     * 活动票种DAO
     */
    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    /**
     * 活动积分历史Dao
     */
    @Autowired
    private val scoreHistoryDao: ScoreHistoryDao? = null

    /**
     * 报名活动用户DAO
     */
    @Autowired
    private val activityUserDao: ActivityUserDao? = null



    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 操作历史记录DAO
     */
    @Autowired
    private val activityUserRecordDao: ActivityUserRecordDao? = null


    /**
     * 获取活动的所有打开记录，并格式化好相关信息
     * @param activityId 活动Id
     */

    fun getRecordsByActivityId(activityId:Int):ResultQuery<Record>{
        var records = create!!.resultQuery("select t1.*, t2.record_time, t2.pictures, t2.notes from activity_user t1   left join activity_user_record t2 on t1.id = t2.activity_user_id where t1.activity_id = ?", activityId)
        return records

    }

    /**
     * 获取打卡记录
     */
    fun getRecord(recordId: Int):ActivityUserRecord{
        return activityUserRecordDao!!.fetchOneById(recordId)
    }

    /**
     * 获取指定活动和用户当天的打卡记录
     */
    fun getTodayRecord(activityUserId:Int):ActivityUserRecord?{
        var item = create!!.resultQuery("select * from  activity_user_record   where activity_user_id = ? and DATE(`record_time`)=curdate()", activityUserId)
        if(item.count() > 0) {
            return item.fetchOneInto(ActivityUserRecord::class.java)
        }
        return null
    }


    /**
     * 保存打卡记录
     */
    fun saveRecord(record : ActivityUserRecord){
        if(record.id > 0){
            activityUserRecordDao!!.update(record)
        } else {
            activityUserRecordDao!!.insert(record)
        }
    }

    /**
     * 取消打卡记录
     */
    fun removeRecord(recordId: Int):Int{
        return activityUserRecordDao!!.deleteById(recordId) as Int
    }

}