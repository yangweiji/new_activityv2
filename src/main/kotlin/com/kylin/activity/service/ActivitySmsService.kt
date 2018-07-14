package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.ActivitySmsDao
import com.kylin.activity.databases.tables.pojos.ActivitySms
import com.kylin.activity.util.CommonService
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by 9kylin on 2018-06-02.
 * 活动短信服务
 * @author Richard C. Hu
 */
@Service
class ActivitySmsService {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动短信数据访问
     */
    @Autowired
    private val activitySmsDao: ActivitySmsDao? = null


    @Autowired
    private val create: DSLContext? = null

    /**
     * 批量发送短信
     */
    fun send(mobiles: String, smsSign: String = "九麒活动吧", templateCode: String, templateParam: String?): Any {
        var response = commonService!!.sendBatchSms(mobiles, smsSign, templateCode, templateParam!!)
        if (response.code == "OK") {
            return true
        }

        return false
    }

    /**
     * 取得发送消息历史
     */
    fun getActivitySmsList(start: String?, end: String?, mobile: String?): Result<Record> {
        var sql = "select * from activity_sms where 1=1 {0} {1} {2} " +
                "order by send_time desc"

        var strCondition = ""
        if (start!!.isNotEmpty()) {
            strCondition = "and date(send_time) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (end!!.isNotEmpty()) {
            strCondition = "and date(send_time) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (mobile!!.isNotEmpty()) {
            strCondition = "and mobiles like '%{0}%'".replace("{0}", mobile!!)
        }
        sql = sql.replace("{2}", strCondition)


        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 保存并更新
     * @param activitySms: 发送消息
     * @return 消息ID
     */
    fun save(activitySms: ActivitySms): Int {
        if (activitySms.id == null) {
            activitySmsDao!!.insert(activitySms)
        } else {
            activitySmsDao!!.update(activitySms)
        }

        return activitySms.id
    }

    /**
     * 查询短信信息
     * @param templateName 短信名称
     * @param displayname 用户名
     * @param title 活动名称
     * @param communityId 团体id
     */
    fun getActivitySmsItem(templateName: String?, displayname: String?, title: String?, communityId: Int): Result<Record> {
        var sql = "select t1.*,t2.displayname,t3.title from activity_sms t1 " +
                "inner join user t2 on t1.send_user_id=t2.id " +
                "left join activity t3 on t1.activity_id=t3.id "+
                "where 1=1 and t3.community_id=? "

        var params = mutableListOf<Any?>()
        if (!templateName.isNullOrBlank()) {
            sql += "and t1.template_name like '%?%' ".replace("?", templateName!!)
            params.add(templateName)
        }
        if (!displayname.isNullOrBlank()) {
            sql += "and t2.displayname like '?' "
            params.add(displayname)
        }
        if (!title.isNullOrBlank()) {
            sql += "and t4.title like '?' "
            params.add(title)
        }
        return create!!.resultQuery(sql, communityId, *params.toTypedArray()).fetch()
    }
}