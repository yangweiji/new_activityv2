package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.AuthUser
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ThirdUserService {
    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val userDao: UserDao? = null

    /**
     * 查询全部用户与积分
     */
    fun getAllUsersAndScores(start: String?, end: String?, username: String?, displayname: String?, real_name: String?, id_card: String?, level: String?, isMember: String?): Result<Record> {
        var sql = "select t1.*, t2.total_score from user t1 " +
                "left join (select user_id, sum(score) total_score " +
                "from score_history " +
                "group by user_id) t2 " +
                "on t1.id = t2.user_id " +
                "where 1=1 {0} {1} {2} {3} {4} {5} {6} {7} "
        var strCondition = ""
        if (!username.isNullOrBlank()) {
            strCondition = "and t1.username like '%{0}%'".replace("{0}", username!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!displayname.isNullOrBlank()) {
            strCondition = "and t1.displayname like '%{0}%'".replace("{0}", displayname!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank()) {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!id_card.isNullOrBlank()) {
            strCondition = "and t1.id_card like '%{0}%'".replace("{0}", id_card!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!level.isNullOrBlank()) {
            strCondition = "and t1.level = {0}".replace("{0}", level!!)
        }
        sql = sql.replace("{4}", strCondition)

        if (!start.isNullOrBlank()) {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!end.isNullOrBlank()) {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{6}", strCondition)

        if (!isMember.isNullOrBlank()) {
            strCondition = "and t1.level > 0"
        }
        sql = sql.replace("{7}", strCondition)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 用户（getRole）角色设置
     */
    fun getRoles(): Map<String, String> {
        val roles = mapOf<String, String>("ADMIN" to "管理员", "PUBLISH" to "发布者")
        return roles
    }

    /**
     * 取得用户的用户名
     */
    fun getUser(username: String): User? {
        var user = userDao!!.fetchOne(Tables.USER.USERNAME, username)
        return user
    }

    /**
     * 取得用户id
     */
    fun getUser(id: Int): User {
        var user = userDao!!.findById(id)
        return user
    }

    /**
     * 认证会员
     */
    fun getMembers(): List<User> {
        var items = create!!.selectFrom(Tables.USER).where(Tables.USER.LEVEL.greaterThan(0)).fetch().map(userDao!!.mapper())
        return items
    }

    /**
     * 获取当前登陆用户的信息
     */
    fun getCurrentUserInfo(): User? {
        val auth = SecurityContextHolder.getContext().authentication
        var user = (auth.principal as AuthUser).user

        return user
    }

    /**
     * 更新用户
     */
    fun update(user: User) {
        userDao!!.update(user)
    }


    /**
     * 全部用户
     */
    fun getAllUsers(): List<User> {
        var items = userDao!!.findAll()
        return items
    }

    /**
     * 添加用户信息
     */
    fun insert(user: User) {
        userDao!!.insert(user)
    }

    /**
     * 删除用户信息
     */
    fun deleteById(id: Int) {
        userDao!!.deleteById(id)
    }

}