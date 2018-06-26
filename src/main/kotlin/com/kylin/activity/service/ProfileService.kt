package com.kylin.activity.service

import com.kylin.activity.databases.tables.daos.UserDao
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * @Auther: Administrator
 * @Date: 2018/6/24 14:33
 * @Description:
 */
@Service
class ProfileService {

    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val userDao: UserDao? = null

    /**
     * 个人信息初始化
     * @param communityId 团体ID
     * @param userId 用户ID
     */
    fun getInitProInformation(communityId: Int?, userId: Int?): Result<Record> {
        val sql = "select t1.*,(select count(activity_id) \n" +
                "from activity_user where user_id=t1.id ) attend_user_count,\n" +
                "(select count(activity_id)\n" +
                " from activity_favorite where user_id=t1.id) favorite_count,\n" +
                "(select ifnull(sum(case  when check_in_time is null then 1 else 0 end ), 0) from \n" +
                " activity_user inner join activity t2 on activity_id = t2.id and t2.end_time > now() where user_id=t1.id ) ne_checked_count,\n" +
                "(select count(activity_id) from activity_user where user_id=t1.id and check_in_time is not null) checked_count,\n" +
                "(select ifnull(sum(score), 0) from score_history where user_id=t1.id) sum_score\n" +
                " from user t1 left join community_user c on t1.id=c.user_id left join community c1 on c.community_id=c1.id where c.community_Id=? and t1.id=? "
        return create!!.resultQuery(sql, communityId, userId).fetch()
    }


    /**
     * 检查当前用户是否是团体的会员
     */
    fun isVip(communityId: Int?, userId: Int?, year: Int): Boolean {
        val sql = "select count(*) counts from community_user where user_id = ? and community_id = ? and level = ?"
        val counts = create!!.fetchOne(sql, userId, communityId, year)
        return counts != null && counts.get("counts", Int::class.java) > 0
    }

    /**
     *
     * 小程序：完善个人信息页面
     * @param userId 用户id
     *
     */
     fun getIntoPersonalInformation(userId: Int?):Result<Record> {
        val sql="select t1.* from user t1 left join community_user t2 on t1.id=t2.user_id where t1.id=? "
        return create!!.resultQuery(sql,userId).fetch()
     }

}