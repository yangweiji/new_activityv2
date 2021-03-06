package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.ScoreHistoryDao
import com.kylin.activity.databases.tables.pojos.ScoreHistory
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ThirdScoreService {
    @Autowired
    private val scoreHistoryDao: ScoreHistoryDao? = null

    @Autowired
    private val dslContext: DSLContext? = null

    /**
     * 用户活动积分明细
     * @param start：创建开始时间
     * @param end：创建结束时间
     * @param activityId：活动编号
     * @param title：活动标题
     * @param username：用户账号
     * @param real_name：用户姓名
     * @param id: 团体ID
     * @return
     */
    fun getUserActivityScores(start: String?, end: String?, activityId: String?, title: String?, username: String?, real_name: String?, id: Int): Result<Record> {
        var sql = "select t1.*, t2.username, t2.displayname, t2.avatar user_avatar, t2.real_name, t3.title " +
                "from score_history t1 " +
                "left join user t2 on t1.user_id = t2.id " +
                "left join activity t3 on t1.activity_id = t3.id " +
                "where 1=1 {0} {1} {2} {3} {4} {5} " +
                "and t1.community_id=? " +
                "order by t1.created desc "
        var strCondition = ""
        if (!title.isNullOrBlank()) {
            strCondition = "and t3.title like '%$title%'"
        }
        sql = sql.replace("{0}", strCondition)

        if (!username.isNullOrBlank()) {
            strCondition = "and t2.username = '$username'"
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank()) {
            strCondition = "and t2.real_name like '%$real_name%'"
        }
        sql = sql.replace("{2}", strCondition)

        if (!start.isNullOrBlank()) {
            strCondition = "and date(t1.created) >= '$start'"
        }
        sql = sql.replace("{3}", strCondition)

        if (!end.isNullOrBlank()) {
            strCondition = "and date(t1.created) <= '$end'"
        }
        sql = sql.replace("{4}", strCondition)

        if (!activityId.isNullOrBlank()) {
            strCondition = "and t1.activity_id = $activityId"
        }
        sql = sql.replace("{5}", strCondition)

        return dslContext!!.resultQuery(sql, id).fetch()
    }

    /**
     * 删除活动积分
     * @param id
     */
    fun deleteById(id: Int?) {
        scoreHistoryDao!!.deleteById(id!!)
    }

    /**
     * 获取用户在一个活动上的积分
     */
    fun getScoreHistories(userId: Int, activityId: Int, communityId: Int): Any? {
        var result = dslContext!!.resultQuery("select * from score_history where user_id=? and activity_id=? and community_id=?", userId, activityId, communityId)
                .fetchOne()

        return result

    }

    /**
     * 获取用户在指定团体上的可用积分
     */
    fun getUseableScore(userId: Int, communityId: Int): Int {
        var sql = "select ifnull(sum(score), 0) counts from score_history where user_id= ? and community_id=?"
        var result = dslContext!!.resultQuery(sql, userId, communityId).fetchOne()
        return result.get("counts", Int::class.java)
    }

    /**
     * 保存更新活动积分
     * @param item
     * @return
     */
    @Transactional
    fun save(item: ScoreHistory): ScoreHistory {
        if (item.id == null) {
            item.created = DateUtil.date().toTimestamp()
            scoreHistoryDao!!.insert(item)
        } else {
            item.created = DateUtil.date().toTimestamp()
            scoreHistoryDao!!.update(item)
        }

        return item
    }


    /**
     * 取得活动积分
     * @param id
     * @return
     */
    fun getScoreHistory(id: Int?): ScoreHistory {
        return scoreHistoryDao!!.findById(id)
    }
}