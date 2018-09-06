package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.PayOrderDao
import com.kylin.activity.databases.tables.pojos.PayOrder
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.text.SimpleDateFormat



/**
 * Created by 9kylin on 2017-12-09.
 */
@Service
class OrderService {

    @Autowired
    private val payOrderDao: PayOrderDao? = null

    @Autowired
    private val dslContext: DSLContext? = null

    /**
     * 取得当前用户的积分明细
     */
    fun getPersonalPayment(id: Int,communityId:Int): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar" +
                ", t3.title activity_title, t4.title ticket_title " +
                "from pay_order t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id " +
                "where t1.status = 2 and t1.user_id = ? and t1.community_id=? " +
                "order by t1.created desc "
        return dslContext!!.resultQuery(sql, id,communityId).fetch()
    }

    /**
     * 用户活动积分明细
     * @param start: 下单开始时间
     * @param end: 下单结束时间
     * @param activityId: 活动编号
     * @param title: 活动标题
     * @param username: 用户账户
     * @param real_name: 真实姓名
     * @param mobile: 报名联系方式
     * @param ticket_title： 活动票种
     * @param extenal_id: 付款单号
     * @param status: 付款状态，1-未付 2-已付
     * @param refund_trade_no: 退款单号
     * @param refund_status: 退款状态，1-已申请退款 2-已完成退款
     * @param community_user: 团体名称
     * @return
     */
    fun getUserActivityPayments(start: String?, end: String?, activityId: String?, title: String?, username: String?, real_name: String?
                                , mobile: String?, ticket_title: String?
                                , extenal_id: String?, status: String?, refund_trade_no: String?, refund_status: String?
                                , community_user:String?): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar" +
                ", t3.title activity_title, t4.title ticket_title, t6.name " +
                ", t5.real_name, t5.mobile " +
                "from pay_order t1 " +
                "left join activity_user t5 on t1.user_id = t5.user_id and t1.activity_id = t5.activity_id and t1.activity_ticket_id = t5.activity_ticket_id " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id " +
                "left join community t6 on t1.community_id = t6.id " +
                "where 1=1 {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} " +
                "order by t1.created desc "
        var strCondition = ""

        //活动编号
        if (!activityId.isNullOrBlank()) {
            strCondition = "and t1.activity_id = $activityId"
        }
        sql = sql.replace("{0}", strCondition)

        //活动标题
        if (!title.isNullOrBlank()) {
            strCondition = "and t3.title like '%$title%'"
        }
        sql = sql.replace("{1}", strCondition)

        //用户账号
        if (!username.isNullOrBlank()) {
            strCondition = "and t2.username = '$username'"
        }
        sql = sql.replace("{2}", strCondition)

        //报名人姓名
        if (!real_name.isNullOrBlank()) {
            strCondition = "and t5.real_name like '%$real_name%'"
        }
        sql = sql.replace("{3}", strCondition)

        //报名人联系方式
        if (!mobile.isNullOrBlank()) {
            strCondition = "and t5.mobile = '$mobile'"
        }
        sql = sql.replace("{4}", strCondition)

        //活动票种
        if (!ticket_title.isNullOrBlank()) {
            strCondition = "and t4.title like '%$ticket_title%'"
        }
        sql = sql.replace("{5}", strCondition)

        if (!extenal_id.isNullOrBlank())
        {
            strCondition = "and t1.extenal_id = '$extenal_id'"
        }
        sql = sql.replace("{6}", strCondition)

        if (!status.isNullOrBlank())
        {
            strCondition = "and t1.status = $status"
        }
        sql = sql.replace("{7}", strCondition)

        if (!refund_trade_no.isNullOrBlank())
        {
            strCondition = "and t1.refund_trade_no = '$refund_trade_no'"
        }
        sql = sql.replace("{8}", strCondition)

        if (!refund_status.isNullOrBlank())
        {
            strCondition = "and t1.refund_status = $refund_status"
        }
        sql = sql.replace("{9}", strCondition)

        //下单开始时间
        if (!start.isNullOrBlank())
        {
            strCondition = "and date(t1.created) >= '$start'"
        }
        sql = sql.replace("{10}", strCondition)
        //下单结束时间
        if (!end.isNullOrBlank())
        {
            strCondition = "and date(t1.created) <= '$end'"
        }
        sql = sql.replace("{11}", strCondition)

        //用户所属团体
        if (!community_user.isNullOrBlank())
        {
            strCondition = "and t6.name like '%$community_user%'"
        }
        sql = sql.replace("{12}", strCondition)

        return dslContext!!.resultQuery(sql).fetch()
    }

    /**
     * 订单汇总统计
     */
    fun  getOrdersStatistics(title: String?, start: String?, end: String?): Result<Record> {
        var sql = "select t.activity_id, t.title, sum(t.price) as amount from " +
        "( " +
        "select t1.id, t1.user_id, t1.activity_id " +
        ", case " +
        "   when t2.title is null then t1.body " +
        "   else t2.title " +
        "   end as title " +
        ", t1.body, t1.price, t1.pay_time " +
        "from pay_order t1 " +
        "left join activity t2 on t1.activity_id = t2.id " +
        "where t1.status = 2 {0} {1} " +
        ") t " +
        "GROUP BY t.activity_id, t.title " +
        "ORDER BY sum(t.price) desc"

        var strCondition = ""
        if (!title.isNullOrBlank())
        {
            strCondition = "and (t2.title like '%{0}%' or t1.body like '%{0}%')".replace("{0}", title!!)
        }
        sql = sql.replace("{0}", strCondition)

        var strTime = ""
        //自定义
        if (!start.isNullOrBlank()) {
            strTime = "and date(t1.pay_time) >= '{0}'".replace("{0}",start!!)
        }
        if (!end.isNullOrBlank())
        {
            strTime += "and date(t1.pay_time) <= '{0}'".replace("{0}",end!!)
        }
        sql = sql.replace("{1}", strTime)

        return dslContext!!.resultQuery(sql).fetch()
    }

    /**
     * 获取订单明细
     */
    fun getOrder(id:Int) : PayOrder {
        return payOrderDao!!.fetchOneById(id)
    }

}
