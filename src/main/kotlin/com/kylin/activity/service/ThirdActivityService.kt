package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.pojos.Activity
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThirdActivityService {

    @Autowired
    private val activityDao:ActivityDao?=null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 团体切换传参id，绑定status
     * 取得最新活动以及每个活动的参与人数信息
     * 前50条记录
     */
    fun getAllActivityUserItemsAndCommunity(status: Int,id:Int): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} " +
                "and ?=t1.community_id "+
                "order by t1.start_time desc " +
                "limit 50"
        var strStatus = ""
        if (status == 1) {
            //未开始的活动
            strStatus = "and t1.start_time > CURDATE()"
        } else if (status == 2) {
            //进行中的活动
            strStatus = "and t1.start_time <= CURDATE() and end_time > CURDATE()"
        } else if (status == 3) {
            //已结束的活动
            strStatus = "and t1.end_time <= CURDATE()"
        }

        sql = sql.replace("{0}", strStatus)
        var items = create!!.resultQuery(sql,id).fetch()
        return items
    }

    /**
     * 团体切换传参id，绑定status,tags,title
     * 按活动标题、分类、状态查询活动
     */
    fun getAllActivityUserItemsAndCommunity(title: String?, tags: String?, status: String?,id:Int): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_user where activity_id = t1.id and check_in_time is not null) check_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} {1} {2} " +
                "and ?=t1.community_id "+
                "order by t1.start_time desc "
        var strCondition = ""
        if (!title.isNullOrBlank()) {
            strCondition = "and t1.title like '%{0}%'".replace("{0}", title!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!tags.isNullOrBlank() && tags != "0") {
            strCondition = "and t1.tags = '{0}'".replace("{0}", tags!!)
        }
        sql = sql.replace("{1}", strCondition)


        if (status == "1") {
            //未开始的活动
            strCondition = "and t1.start_time > CURDATE()"
        } else if (status == "2") {
            //进行中的活动
            strCondition = "and t1.start_time <= CURDATE() and end_time > CURDATE()"
        } else if (status == "3") {
            //已结束的活动
            strCondition = "and t1.end_time <= CURDATE()"
        }
        sql = sql.replace("{2}", strCondition)

        var items = create!!.resultQuery(sql,id).fetch()
        return items
    }

    /**
     * 取得活动、其他信息
     */
    fun getActivityAndOthers(id: Int): Record? {
        var sql = "select t1.* from activity t1 where t1.id = ?"
        var item = create!!.fetchOne(sql, id)
        return item
    }

    /**
     * 活动报名信息
     */
    fun getAttendUsers(start: String?, end: String?, activityId: String?, title: String?, mobile: String?, real_name: String?, ticket_title: String?, checked: String?, status: String?, other_info: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.id, t1.user_id, t1.activity_id, t1.activity_ticket_id, t1.created, t1.created_by " +
                ", t1.attend_time, t1.check_in_time, t1.real_name, t1.mobile, t1.other_info, t1.price, t1.score " +
                ", t2.displayname, t2.avatar user_avatar " +
                ", t3.title, t4.price as activity_price, t4.title as ticket_title " +
                "from activity_user t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id and t3.Id = t4.activity_id " +
                "where 1=1 {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} " +
                "order by t1.created desc " +
                "{99}"
        var strCondition = ""
        if (!mobile.isNullOrBlank()) {
            strCondition = "and t1.mobile = '{0}'".replace("{0}", mobile!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!title.isNullOrBlank()) {
            strCondition = "and t3.title like '%{0}%'".replace("{0}", title!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank()) {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!activityId.isNullOrBlank()) {
            strCondition = "and t1.activity_id = {0}".replace("{0}", activityId!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!checked.isNullOrBlank()) {
            strCondition = "and t1.check_in_time is not null"
        }
        sql = sql.replace("{4}", strCondition)

        if (!ticket_title.isNullOrBlank()) {
            strCondition = "and t4.title like '%{0}%'".replace("{0}", ticket_title!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!start.isNullOrBlank()) {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{6}", strCondition)

        if (!end.isNullOrBlank()) {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{7}", strCondition)

        if (!status.isNullOrBlank()) {
            strCondition = " and t1.status = $status "
        } else {
            strCondition = " and (t1.status is null or t1.status = 0)  "
        }
        sql = sql.replace("{8}", strCondition)

        if (!other_info.isNullOrBlank()) {
            strCondition = "and t1.other_info like '%{0}%'".replace("{0}", other_info!!)
        }
        sql = sql.replace("{9}", strCondition)

        var strLimit = ""
        if (strCondition.isNullOrBlank()) {
            //如果无条件，默认取得最近的100条记录
            //strLimit = "limit 100"
        }
        sql = sql.replace("{99}", strLimit)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }


    /**
     * 活动统计
     */
    fun getActivityStatisticsByTicket(activityId: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.activity_ticket_id, t2.title ticket_title, t1.attendcount, t1.checkcount " +
                "from (select activity_ticket_id, count(attend_time) as attendcount, count(check_in_time) as checkcount from activity_user where activity_id = ? group by activity_ticket_id) t1 " +
                "inner join activity_ticket t2 " +
                "on t1.activity_ticket_id = t2.id"
        var items = create!!.resultQuery(sql, activityId).fetch()
        return items
    }

    /**
     * 活动报名人数
     */
    fun getActivityAttendCount(activityId: String?): Any {
        var sql = "select count(*) from activity_user where activity_id = ?"
        var count = create!!.resultQuery(sql, activityId).single()[0]
        return count
    }


    /**
     * 活动签到人数
     */
    fun getActivityCheckCount(activityId: String?): Any? {
        var sql = "select count(*) from activity_user where activity_id = ? and check_in_time is not null"
        var count = create!!.resultQuery(sql, activityId).single()[0]
        return count
    }

    /**
     * 删除活动
     */
    fun deleteById(id: Int) {
        activityDao!!.deleteById(id)
    }

    /**
     * 取得活动
     */
    fun getActivity(id: Int): Activity? {
        return activityDao!!.fetchOne(Tables.ACTIVITY.ID, id)
    }

    /**
     * 获取用户在一个活动上的积分
     */
    fun getScoreHistories(userId:Int, activityId:Int):Any?{
        var result =create!!.resultQuery("select * from score_history where user_id=? and activity_id=?", userId, activityId)
                .fetchOne()

        return result

    }
}