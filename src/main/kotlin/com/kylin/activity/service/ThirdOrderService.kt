package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.PayOrderDao
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThirdOrderService {
    @Autowired
    private val payOrderDao: PayOrderDao? = null

    @Autowired
    private val dslContext: DSLContext? = null

    /**
     * 用户活动积分明细
     * @return
     */
    fun getUserActivityPayments(start: String?, end: String?, title: String?, username: String?, real_name: String?
                                , extenal_id: String?, status: String?, refund_trade_no: String?, refund_status: String?
                                , communityId: Int): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar, t2.real_name" +
                ", t3.title activity_title, t4.title ticket_title " +
                "from pay_order t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id " +
                "where t1.community_id = ? {0} {1} {2} {3} {4} {5} {6} {7} {8} " +
                "order by t1.created desc "
        var strCondition = ""
        if (title != null && !title.isEmpty()) {
            strCondition = "and t3.title like '%{0}%'".replace("{0}", title)
        }
        sql = sql.replace("{0}", strCondition)

        if (username != null && !username.isEmpty()) {
            strCondition = "and t2.username = '{0}'".replace("{0}", username)
        }
        sql = sql.replace("{1}", strCondition)

        if (real_name != null && !real_name.isEmpty()) {
            strCondition = "and t2.real_name like '%{0}%'".replace("{0}", real_name)
        }
        sql = sql.replace("{2}", strCondition)

        if (!start.isNullOrBlank())
        {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!end.isNullOrBlank())
        {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{4}", strCondition)

        if (!extenal_id.isNullOrBlank())
        {
            strCondition = "and t1.extenal_id like '%{0}%'".replace("{0}", extenal_id!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!status.isNullOrBlank())
        {
            strCondition = "and t1.status = {0}".replace("{0}", status!!)
        }
        sql = sql.replace("{6}", strCondition)

        if (!refund_trade_no.isNullOrBlank())
        {
            strCondition = "and t1.refund_trade_no like '%{0}%'".replace("{0}", refund_trade_no!!)
        }
        sql = sql.replace("{7}", strCondition)

        if (!refund_status.isNullOrBlank())
        {
            strCondition = "and t1.refund_status = {0}".replace("{0}", refund_status!!)
        }
        sql = sql.replace("{8}", strCondition)

        return dslContext!!.resultQuery(sql, communityId).fetch()
    }


    /**
     * 团体组织的订单汇总统计
     * @param title: 标题
     * @param start: 付款起始日期
     * @param end: 付款结束日期
     * @param communityId: 团体组织ID
     * @return 订单统计列表信息
     */
    fun  getOrdersStatistics(title: String?, start: String?, end: String?, communityId: Int): Result<Record> {
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
                "where t1.community_id = ? and t1.status = 2 {0} {1} " +
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

        return dslContext!!.resultQuery(sql, communityId).fetch()
    }
}