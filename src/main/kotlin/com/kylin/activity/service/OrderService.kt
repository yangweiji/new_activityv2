package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.PayOrderDao
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    fun getPersonalPayment(id: Int): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar" +
                ", t3.title activity_title, t4.title ticket_title " +
                "from pay_order t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id " +
                "where t1.status = 2 and t1.user_id = ? " +
                "order by t1.created desc "
        return dslContext!!.resultQuery(sql, id).fetch()
    }

    /**
     * 用户活动积分明细
     * @return
     */
    fun getUserActivityPayments(title: String?, username: String?, displayname: String?): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar" +
                ", t3.title activity_title, t4.title ticket_title " +
                "from pay_order t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "left join activity_ticket t4 on t1.activity_ticket_id = t4.id " +
                "where 1=1 and t1.status = 2 {0} {1} {2} " +
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

        if (displayname != null && !displayname.isEmpty()) {
            strCondition = "and t2.displayname like '%{0}%'".replace("{0}", displayname)
        }
        sql = sql.replace("{2}", strCondition)

        return dslContext!!.resultQuery(sql).fetch()
    }

}
