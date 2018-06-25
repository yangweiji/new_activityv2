package com.kylin.activity.service

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
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 个人信息初始化
     */
    fun getInitProInformation(userId:Int?):Result<Record>{
        val sql="select count(activity_id) counts,t2.avatar,t2.displayname,t2.level,t2.username,t2.is_real,t1.user_id from activity_user t1 left join user t2 on t1.user_id=t2.id where t1.user_id=? \n" +
                "union all" +
                " select count(activity_id)  counts,t2.avatar,t2.displayname,t2.level,t2.username,t2.is_real,t1.user_id from activity_favorite t1 left join user t2 on t1.user_id=t2.id where t1.user_id=?  \n" +
                "union all " +
                "select ifnull(sum(case  when check_in_time is null then 1 else 0 end ), 0)  counts,t3.avatar,t3.displayname,t3.level,t3.username,t3.is_real,t1.user_id from  \n" +
                " activity_user t1 left join user t3 on t1.user_id=t3.id inner join activity t2 on t1.activity_id = t2.id and t1.user_id= ?   and t2.end_time > now() \n" +
                "  union all " +
                " select count(activity_id) counts,t2.avatar,t2.displayname,t2.level,t2.username,t2.is_real,t1.user_id from activity_user t1 left join user t2 on t1.user_id=t2.id where t1.user_id=?  \n" +
                "and check_in_time is not null " +
                "union all " +
                " select ifnull(sum(score), 0) counts ,t2.avatar,t2.displayname,t2.level,t2.username,t2.is_real,t1.user_id from score_history t1 left join user t2 on t1.user_id=t2.id where t1.user_id=? \n"
       return create!!.resultQuery(sql,userId,userId,userId,userId,userId).fetch()
    }

}