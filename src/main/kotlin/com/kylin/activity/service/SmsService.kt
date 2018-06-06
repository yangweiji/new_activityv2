package com.kylin.activity.service


import com.kylin.activity.databases.tables.daos.ActivitySmsDao
import com.kylin.activity.databases.tables.pojos.ActivitySms
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 短信服务
 * @author Richard C. Hu
 */
@Service
class SmsService {

    /**
     * 短信DAO
     */
    @Autowired
    private val activitySmsDao: ActivitySmsDao? = null

    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 添加短信记录
     * @param activitySms: 短信信息
     */
    fun insert(activitySms: ActivitySms) {
        activitySmsDao!!.insert(activitySms)
    }

    /**
     * 取得短信列表信息
     * @return 短信列表信息
     */
    fun getActivitySmsList(): Result<Record> {
        var sql = "select t1.*, t2.displayname from activity_sms t1 inner joint user t2 on t1.send_user_id = t2.id"
        return create!!.resultQuery(sql).fetch()
    }

}