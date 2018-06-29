package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger

/*data class InfoData(
        var counts:Int=0

)*/


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
    private val userDao: UserDao? = null


    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null


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
     * 小程序个人中心积分信息
     *@param userId 用户id
     *@param communityId 团体id
     */
    fun getActivityIntegral(communityId: Int?, userId: Int?): Result<Record> {
        var sql = "select t1.*, t2.title from score_history t1 left join activity t2 on t1.activity_id = t2.id where t1.user_id=? and t1.community_id=? order by t1.created desc"
        val scores = create!!.resultQuery(sql, userId, communityId).fetch()
        return scores
    }


    /**
     * 更新并保存用户信息
     */
    fun updateUserInfo(user: User) {
        userDao!!.update(user)
    }


    /**
     * 我的活动
     * @param type 活动类型
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun myActivities(type: Int?,userId:Int?,communityId: Int?): Result<Record> {
        var activitySql =   " select \n" +
                            "t1.id, \n" +
                            "t1.title,\n" +
                            "t1.community_id,\n" +
                            "t1.start_time, \n" +
                            "t1.end_time,\n" +
                            " t1.avatar, \n" +
                            "  t1.activity_type,\n" +
                            " t2.attend_count,\n" +
                            " t3.favorite_count\n" +
                            "from activity as t1\n" +
                            "left  join (\n" +
                            "select \n" +
                            " activity_user.activity_id, activity_user.user_id,\n" +
                            "ifnull(count(activity_user.id), 0) as attend_count\n" +
                            "from activity_user where activity_user.user_id=?\n" +
                            "group by activity_user.activity_id\n" +
                            " ) as t2\n" +
                            "on t1.id = t2.activity_id \n" +
                            "left join(\n" +
                            "select \n" +
                            "activity_favorite.activity_id,activity_favorite.user_id,\n" +
                            "ifnull(count(activity_favorite.id), 0) as favorite_count\n" +
                            "from activity_favorite where activity_favorite.user_id=? \n" +
                            "group by activity_favorite.activity_id\n"+
                            ") as t3 on t1.id=t3.activity_id\n"+
                            "where t1.public = true\n"+
                            "order by t1.start_time desc"
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
        return create!!.resultQuery(sql,userId,userId,userId,communityId).fetch()

    }
}