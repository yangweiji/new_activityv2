package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger

data class InfoData(
        /**
         * 已参加活动数
         */
        var joinedCount: Int = 0,
        /**
         * 需签到活动数
         */
        var needCheckedCount: Int = 0,
        /**
         * 已签到活动数
         */
        var checkedCount: Int = 0,
        /**
         * 收藏活动数
         */
        var likedCount: Int = 0,
        /**
         * 积分总额
         */
        var sumScores: Int = 0
)


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
     * 检查当前用户是否是团体的会员
     */
    fun isVip(communityId: Int?, userId: Int?, year: Int): Boolean {
        val sql = "select count(*) counts from community_user where user_id = ? and community_id = ? and level = ?"
        val counts = create!!.fetchOne(sql, userId, communityId, year)
        return counts != null && counts.get("counts", Int::class.java) > 0
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
     * 用户已参加活动数
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun activityAttendCounts(userId: Int?, communityId: Int?): InfoData {
        var infoData = InfoData()
        var sql = "select count(activity_id) counts from activity_user t1 " +
                "left join activity t2 on t1.activity_id=t2.id " +
                "left join community t3 on t2.community_id=t3.id " +
                "where t1.user_id=? and t2.community_id=? "
        infoData.joinedCount = (create!!.fetchValue(sql, userId, communityId) as Long).toInt()
        return infoData
    }

    /**
     * 用户收藏活动数
     * fetchValue():执行一个包含普通SQL的新查询.
     * SQL中包含的绑定变量必须与绑定参数* bindings参数中一样多
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun favoriteActivityCounts(userId: Int?, communityId: Int?): InfoData {
        var infoData = InfoData()
        var sql = "select count(activity_id) counts from activity_favorite t1 " +
                "left join activity t2 on t1.activity_id=t2.id left join community t3 " +
                "on t2.community_id=t3.id where t1.user_id= ? and t2.community_id=? "
        infoData.likedCount = (create!!.fetchValue(sql, userId, communityId) as Long).toInt()
        return infoData
    }


    /**
     * 用户需签到的活动数
     *  @param userId 用户id
     *  @param communityId 团体id
     */
    fun noCheckedActivityCounts(userId: Int?, communityId: Int?): InfoData {
        var infoData = InfoData()
        var sql = " select ifnull(sum(case  when check_in_time is null then 1 else 0 end ), 0)  counts from activity_user t1 " +
                "inner join activity t2 on t1.activity_id = t2.id  and t2.end_time > now() " +
                "left join community t3 on t2.community_id=t3.id where t1.user_id= ? and t2.community_id=?  "
        infoData.needCheckedCount = (create!!.fetchValue(sql, userId, communityId) as BigInteger).toInt()
        return infoData
    }

    /**
     * 用户已签到活动数
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun checkedActivityCounts(userId: Int?, communityId: Int?): InfoData {
        var infoData = InfoData()
        var sql = "select count(activity_id) counts from activity_user t1 " +
                "left join activity t2 on t1.activity_id=t2.id " +
                "left join community t3 on t2.community_id=t3.id " +
                " where t1.user_id= ? and t2.community_id=? and check_in_time is not null "
        infoData.checkedCount = (create!!.fetchValue(sql, userId, communityId) as Long).toInt()
        return infoData
    }

    /**
     * 用户积分总额
     * @param userId 用户id
     * @param communityId 团体id
     */
    fun sumScores(userId: Int?, communityId: Int?): InfoData {
        var infoData = InfoData()
        var sql = "select ifnull(sum(score), 0) counts from score_history t1 " +
                "left join activity t2 on t1.activity_id=t2.id " +
                "where t1.user_id= ? and t1.community_id=? "
        infoData.sumScores = (create!!.fetchValue(sql, userId, communityId) as BigInteger).toInt()
        return infoData
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
                            "LEFT JOIN(select activity_favorite.activity_id, \n" +
                            "ifnull(count(activity_favorite.id), 0) as favorite_count,activity_favorite.user_id\n" +
                            "from activity_favorite\n" +
                            "group by activity_favorite.activity_id ) t3 on t1.id=t3.activity_id"

        var sql = if (type == 1) {
            //已参加活动数
            "select t.*,t5.avatar as user_avatar from ($activitySql) t inner join activity_user t4 " +
                    "on t.id=t4.activity_id \n" +
                    "left join user t5 on t4.user_id=t5.id\n" +
                    "where t4.user_id=?\n" +
                    "and t.community_id=?"
        } else if (type == 2) {
            //收藏活动数
            "select t.*,t5.avatar as user_avatar from ($activitySql) t inner join activity_favorite t4 " +
                    "on t.id=t4.activity_id \n" +
                    "left join user t5 on t4.user_id=t5.id\n" +
                    "where t4.user_id=?\n" +
                    "and t.community_id=?"
        } else if (type == 3) {
            //需签到活动数
            "select t.*,t5.avatar as user_avatar from ($activitySql) t inner join activity_user t4 on t.id=t4.activity_id\n" +
                    "left join user t5 on t4.user_id=t5.id\n" +
                    "and t4.check_in_time is null and t.end_time > now()\n "+
                    "where t4.user_id=?\n" +
                    "and t.community_id=?"
        } else {
            //已签到活动数
            "select t.*,t5.avatar as user_avatar from ($activitySql) t inner join activity_user t4 on t.id=t4.activity_id\n" +
                    "and t4.check_in_time is not null\n"+
                    "left join user t5 on t4.user_id=t5.id\n" +
                    "where t4.user_id=?\n" +
                    "and t.community_id=?"
        }
        return create!!.resultQuery(sql,userId,communityId).fetch()

    }
}