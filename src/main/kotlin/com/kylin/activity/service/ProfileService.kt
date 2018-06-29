package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: Administrator
 * @Date: 2018/6/24 14:33
 * @Description:
 */
@Service
class ProfileService {

    /**
     * 用户DAO
     */
    @Autowired
    private val userDao:UserDao?=null
    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 个人信息初始化
     * @param communityId 团体ID
     * @param userId 用户ID
     */
    fun getInitProInformation(communityId: Int?,userId: Int?):Result<Record>{
        val sql="select t1.*,(select count(activity_id) \n" +
                "from activity_user where user_id=t1.id ) attend_user_count,\n" +
                "(select count(activity_id)\n" +
                " from activity_favorite where user_id=t1.id) favorite_count,\n" +
                "(select ifnull(sum(case  when check_in_time is null then 1 else 0 end ), 0) from \n" +
                " activity_user inner join activity t2 on activity_id = t2.id and t2.end_time > now() where user_id=t1.id ) ne_checked_count,\n" +
                "(select count(activity_id) from activity_user where user_id=t1.id and check_in_time is not null) checked_count,\n" +
                "(select ifnull(sum(score), 0) from score_history where user_id=t1.id) sum_score\n" +
                " from user t1 left join community_user c on t1.id=c.user_id left join community c1 on c.community_id=c1.id where c.community_Id=? and t1.id=? "
        return create!!.resultQuery(sql,communityId,userId).fetch()
    }


    /**
     * 检查当前用户是否是团体的会员
     */
    fun isVip(communityId: Int?, userId: Int?, year: Int): Boolean {
        val sql = "select count(*) counts from community_user where user_id = ? and community_id = ? and level = ?"
        val counts = create!!.fetchOne(sql, userId, communityId, year)
        return counts != null && counts.get("counts", Int::class.java) > 0
    }
    fun getActivityIntegral(communityId: Int?,userId: Int?):Result<Record>{
        var sql = "select t1.*, t2.title from score_history t1 left join activity t2 on t1.activity_id = t2.id where t1.user_id=? and t1.community_id=? order by t1.created desc"
        val scores = create!!.resultQuery(sql,userId,communityId).fetch()
        return scores
    }


    /**
     *
     * 小程序：完善个人信息页面
     * @param userId 用户id
     *
     */
    fun getIntoPersonalInformation(userId: Int?):Result<Record> {
        val sql="select t1.* from user t1 where t1.id=? "
        return create!!.resultQuery(sql,userId).fetch()
    }


    /**
     * 个人中心4个活动数+积分总额
     * counts:已参加/我喜欢/需签到/已签到/积分总额
     * @param userId 用户编号
     * @param communityId 团体编号
     */
    fun getPersonalInfoCounts(userId: Int?,communityId: Int?):Result<Record>{
        val sql="select count(activity_id) counts from activity_user t1 inner join activity t2 on t1.activity_id=t2.id and t1.user_id=? and t2.community_id=? \n" +
                "union all\n" +
                "select count(activity_id) counts from activity_favorite t1 inner join activity t2 on t1.activity_id=t2.id  and t1.user_id=? and t2.community_id=? \n" +
                "union all\n" +
                "select ifnull(sum(case  when check_in_time is null then 1 else 0 end ), 0)  \n" +
                "counts from activity_user t1 inner join activity t2 on t1.user_id=? and t2.community_id=? and t2.end_time > now()\n" +
                "union all\n" +
                "select count(activity_id) counts from activity_user t1 inner join activity t2 on t1.activity_id=t2.id  \n" +
                "and t1.user_id=? and t2.community_id=?  and check_in_time is not null \n" +
                "union all\n" +
                "select ifnull(sum(score), 0) counts from score_history where user_id=? and community_id=?"
        return create!!.resultQuery(sql,userId,communityId,userId,communityId,userId,
                communityId,userId,communityId,userId,communityId).fetch()

    }


    /**
     * 我的活动
     * @param type 活动类型
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun myActivities(type: Int?,userId:Int?,communityId: Int?): Result<Record> {
        var activitySql = "select t1.id,t1.community_id,t1.end_time,t1.avatar,t1.start_time,t1.title,t2.attend_count,\n" +
                "t3.favorite_count from activity t1 left join \n" +
                "(select activity_user.activity_id,\n" +
                "ifnull(count(activity_user.id), 0) as attend_count,activity_user.user_id\n" +
                "from activity_user\n" +
                "group by activity_user.activity_id) t2 on t1.id=t2.activity_id\n" +
                "left join(select activity_favorite.activity_id, \n" +
                "ifnull(count(activity_favorite.id), 0) as favorite_count,activity_favorite.user_id\n" +
                "from activity_favorite\n" +
                "group by activity_favorite.activity_id ) t3 on t1.id=t3.activity_id"
        var sql = if (type == 1) {
            //已参加活动数
            "select t.* from ($activitySql) t inner join activity_user t4 " +
                    "on t.id=t4.activity_id \n"+
                    "and t4.user_id=?\n"+
                    "and t.community_id=?"
        } else if (type == 2) {
            //收藏活动数
            "select t.* from ($activitySql) t inner join activity_favorite t4 " +
                    "on t.id=t4.activity_id \n" +
                    "and t4.user_id=?\n"+
                    "and t.community_id=?"
        } else if (type == 3) {
            //需签到活动数
            "select t.* from ($activitySql) t inner join activity_user t4 on t.id=t4.activity_id\n" +
                    "and t4.check_in_time is null and t.end_time > now()\n "+
                    "and t4.user_id=?\n" +
                    "and t.community_id=?"
        } else {
            //已签到活动数
            "select t.* from ($activitySql) t inner join activity_user t4 on t.id=t4.activity_id\n" +
                    "and t4.check_in_time is not null\n"+
                    "and t4.user_id=?\n" +
                    "and t.community_id=?"
        }
        return create!!.resultQuery(sql,userId,communityId).fetch()

    }


    /**
     * 更新并保存用户信息
     */
    fun updateUserInfo(user: User) {
        userDao!!.update(user)
    }
}