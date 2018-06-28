package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityTicketDao
import com.kylin.activity.databases.tables.daos.ActivityUserDao
import com.kylin.activity.databases.tables.daos.PayOrderDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityTicket
import com.kylin.activity.databases.tables.pojos.ActivityUser
import com.kylin.activity.databases.tables.pojos.PayOrder
import com.xiaoleilu.hutool.date.DateTime
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

/**
 * 第三方团体组织活动服务
 * @author Richard C. Hu
 */
@Service
@CacheConfig(cacheNames = ["activities"])
class ThirdActivityService {

    /**
     * 活动数据访问
     */
    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 活动票种数据访问
     */
    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    /**
     * 活动报名用户数据访问
     */
    @Autowired
    private val activityUserDao: ActivityUserDao? = null

    /**
     * 订单数据访问
     */
    @Autowired
    private val payOrderDao: PayOrderDao? = null

    /**
     * 数据访问上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 团体切换传参id，绑定status
     * 取得最新活动以及每个活动的参与人数信息
     * 前50条记录
     * @param status: 活动按照周期查询的标识分类, 1:未开始的活动 2:进行中的活动 3:已结束的活动
     * @param id: 团体组织ID
     * @return 活动列表信息
     */
    fun getAllActivityUserItemsAndCommunity(status: Int, id: Int): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} " +
                "and ?=t1.community_id " +
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

        var items = create!!.resultQuery(sql, id).fetch()
        return items
    }

    /**
     * 团体切换传参id，绑定status,tags,title
     * 按活动标题、分类、状态查询活动
     * @param activityId: 活动ID
     * @param title: 活动标题
     * @param tags: 活动标签分类
     * @param status: 活动按照周期查询的标识分类, 1:未开始的活动 2:进行中的活动 3:已结束的活动
     * @param id: 团体组织ID
     * @return 活动列表信息
     */
    fun getAllActivityUserItemsAndCommunity(activityId: String?, title: String?, tags: String?, status: String?, id: Int): Result<Record> {
        var sql = "select t1.*, t2.displayname, t2.avatar user_avatar," +
                "(select count(*) from activity_user where activity_id = t1.id) attend_user_count, " +
                "(select count(*) from activity_user where activity_id = t1.id and check_in_time is not null) check_user_count " +
                "from activity t1 left join user t2 on t1.created_by = t2.id " +
                "where 1=1 {0} {1} {2} {3} " +
                "and ?=t1.community_id " +
                "order by t1.start_time desc "
        var strCondition = ""
        if (!title.isNullOrBlank()) {
            strCondition = "and t1.title like '%$title%'"
        }
        sql = sql.replace("{0}", strCondition)

        if (!tags.isNullOrBlank() && tags != "0") {
            strCondition = "and t1.tags = '$tags'"
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

        if (activityId!!.isNotEmpty()) {
            strCondition = "and t1.id = $activityId"
        }
        sql = sql.replace("{3}", strCondition)

        return create!!.resultQuery(sql, id).fetch()
    }

    /**
     * 取得活动、其他信息
     * @param id: 活动ID
     * @return 单个活动记录
     */
    fun getActivityAndOthers(id: Int): Record? {
        var sql = "select t1.* from activity t1 where t1.id = ?"
        var item = create!!.fetchOne(sql, id)
        return item
    }

    /**
     * 活动报名信息
     * @param communityId: 团体组织ID
     * @param start: 活动创建开始日期
     * @param end: 活动创建结束日期
     * @param activityId: 活动ID
     * @param title: 活动标题
     * @param mobile: 报名用户手机号
     * @param real_name: 报名用户姓名
     * @param ticket_title: 票种标题
     * @param checked: 是否签到
     * @param status: 中签状态
     * @param other_info: 报名填写字段值信息
     * @return 活动报名列表信息
     */
    fun getAttendUsers(communityId: Int, start: String?, end: String?, activityId: String?, title: String?, mobile: String?, real_name: String?, ticket_title: String?, checked: String?, status: String?, other_info: String?): Result<Record> {
        //构建数据源
        var sql = "select t1.id, t1.user_id, t1.activity_id, t1.activity_ticket_id, t1.created, t1.created_by " +
                ", t1.attend_time, t1.check_in_time, t1.real_name, t1.mobile, t1.other_info, t1.price, t1.score, t1.status " +
                ", t2.displayname, t2.avatar user_avatar " +
                ", t3.title, t4.price as activity_price, t4.title as ticket_title " +
                "from activity_user t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id and t3.Id = t4.activity_id " +
                "where t3.community_id = ? {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} " +
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

        var items = create!!.resultQuery(sql, communityId).fetch()
        return items
    }


    /**
     * 活动统计
     * @param activityId: 活动ID
     * @return 活动统计列表信息
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
     * @param activityId: 活动ID
     * @return 活动报名人数
     */
    fun getActivityAttendCount(activityId: String?): Any {
        var sql = "select count(*) from activity_user where activity_id = ?"
        var count = create!!.fetchValue(sql, activityId)
        return count
    }


    /**
     * 活动签到人数
     * @param activityId: 活动ID
     * @return 活动签到人数
     */
    fun getActivityCheckCount(activityId: String?): Any? {
        var sql = "select count(*) from activity_user where activity_id = ? and check_in_time is not null"
        var count = create!!.fetchValue(sql, activityId)
        return count
    }

    /**
     * 删除活动
     * @param id: 活动ID
     */
    @CacheEvict(allEntries = true)
    fun deleteById(id: Int) {
        activityDao!!.deleteById(id)
    }

    /**
     * 取得活动
     * @param id: 活动ID
     * @return 单个活动对象信息
     */
    fun getActivity(id: Int): Activity? {
        return activityDao!!.fetchOne(Tables.ACTIVITY.ID, id)
    }

    /**
     * 取得活动对应的票种集合
     * @param id: 活动ID
     * @return 活动票种列表信息
     */
    fun getActivityTickets(id: Int): List<ActivityTicket>? {
        return activityTicketDao!!.fetchByActivityId(id)
    }

    /**
     * 添加活动
     * @param activity: 活动信息
     */
    @CacheEvict(allEntries = true)
    fun insert(activity: Activity?) {
        activityDao!!.insert(activity)
    }

    /**
     * 添加活动票种
     * @param list: 活动票种列表
     */
    fun insertActivityTickets(list: List<ActivityTicket>) {
        activityTicketDao!!.insert(list)
    }

    /**
     * 编辑活动
     * @param activity: 活动信息
     */
    @CacheEvict(allEntries = true)
    fun update(activity: Activity?) {
        activityDao!!.update(activity)
    }

    /**
     * 删除活动对应的票种
     * @param id: 活动ID
     */
    fun deleteActivityTickets(id: Int?) {
        create!!.deleteFrom(Tables.ACTIVITY_TICKET)
                .where(Tables.ACTIVITY_TICKET.ACTIVITY_ID.eq(id))
                .execute()
    }

    /**
     * 更新活动报名状态
     * @param id: 报名ID
     * @param status： 报名中签结果状态
     */
    fun updateActivityUserStatus(id: Int, status: Int?): Any {
        val sql = "update activity_user set status = ? where id = ?"
        return create!!.execute(sql, status, id)
    }

    /**
     * 取得活动报名用户
     * @param id: 报名ID
     * @return 单个活动报名信息
     */
    fun getActivityUser(id: Int): ActivityUser {
        return activityUserDao!!.fetchOneById(id)
    }

    /**
     * 取得报名用户的缴费订单
     * @param id: 报名ID
     * @return 缴费订单信息
     */
    fun getActivityUserOrder(id: Int): PayOrder? {
        var order =  create!!.fetchOne("select t2.* from activity_user t1 " +
                "inner join pay_order t2 on t1.user_id = t2.user_id " +
                "and t1.activity_id = t2.activity_id " +
                "and t1.activity_ticket_id = t2.activity_ticket_id " +
                "and t2.status = 2 " +
                "and t1.id=? " +
                "order by t2.created desc " +
                "limit 1", id)
        if (order != null)
        {
            return order.into(PayOrder::class.java)
        }

        return null
    }

    /**
     * 取得报名用户的缴费订单
     * @param id: 报名ID
     * @param status: 订单状态
     * @return 缴费订单信息
     */
    fun getPayOrder(id: Int, status: Int): PayOrder? {
        var order = create!!.selectFrom(Tables.PAY_ORDER)
                .where(Tables.PAY_ORDER.ID.eq(id))
                .and(Tables.PAY_ORDER.STATUS.eq(status))
                .fetchOneInto(PayOrder::class.java)
        return order
    }

    /**
     * 取得报名用户的缴费订单
     * @param id: 报名ID
     * @param status: 订单状态
     * @param refund_status: 订单退款状态
     * @return 缴费订单信息
     */
    fun getActivityUserOrder(id: Int, status: Int, refund_status: Int?): PayOrder? {
        var order = create!!.fetchOne("select t2.* from activity_user t1 " +
                "inner join pay_order t2 on t1.user_id = t2.user_id " +
                "and t1.activity_id = t2.activity_id " +
                "and t1.activity_ticket_id = t2.activity_ticket_id " +
                "and t2.status=? " +
                "and t2.refund_status=? " +
                "and t1.id=? " +
                "order by created desc " +
                "limit 1", status, refund_status, id)
        if (order != null)
        {
            return order.into(PayOrder::class.java)
        }
        return null
    }

    /**
     * 更新报名用户缴费订单信息
     * @param order: 订单信息
     */
    fun updateActivityUserOrder(order: PayOrder) {
        payOrderDao!!.update(order)
    }

    /**
     * 取得指定活动及用户的报名数
     * @param activityId: 活动ID
     * @param userId: 用户ID
     * @return 报名数
     */
    fun getActivityUserCount(activityId: Int, userId: Int): Any {
        var sql = "select count(*) from activity_user where activity_id = ? and user_id = ?"
        return create!!.fetchValue(sql, activityId, userId)
    }

    /**
     * 添加用户报名记录
     * @param activityUser: 用户报名记录
     */
    fun insertActivityUser(activityUser: ActivityUser) {
        activityUserDao!!.insert(activityUser)
    }

    /**
     * 删除活动报名记录
     * @param id: 报名ID
     */
    fun deleteActivityUser(id: Int) {
        activityUserDao!!.deleteById(id)
    }

    /**
     * 取得所有活动信息，返回List
     */
    fun getAllActivities(): List<Activity> {
        var items = activityDao!!.findAll()
        return items
    }
}