package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.CommunityUserDao
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.AuthUser
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * 第三方团体组织用户服务
 * @author Richard C. Hu
 */
@Service
class ThirdUserService {

    /**
     * 数据访问上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 用户数据访问
     */
    @Autowired
    private val userDao: UserDao? = null

    /**
     * 团体组织用户关联数据访问
     */
    @Autowired
    private val communityUserDao: CommunityUserDao? = null

    /**
     * 查询全部用户与积分
     */
    fun getAllUsersAndScores(start: String?, end: String?, username: String?, displayname: String?, real_name: String?, id_card: String?, level: String?, isMember: String?): Result<Record> {
        var sql = "select t1.*, t2.total_score from user t1 " +
                "left join (select user_id, sum(score) total_score from score_history group by user_id) t2 " +
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
     * 查询全部用户与积分
     * @param communityId: 团体组织ID
     * @param start: 创建起始日期
     * @param end: 创建结束日期
     * @param username: 登录账号
     * @param displayname: 显示名称
     * @param real_name: 真实姓名
     * @param id_card: 身份证号
     * @param level: 会员年度
     * @param isMember: 是否会员
     * @param is_black: 是否黑名单
     * @param role: 团体角色
     * @return 用户列表信息
     */
    fun getCommunityUsersAndScores(communityId: Int, start: String?, end: String?, username: String?, displayname: String?, real_name: String?, id_card: String?, level: String?, isMember: String?, is_black: String?, role: String?): Result<Record> {
        var sql = "select t1.*, t2.total_score, t3.role as role_name, t3.level as level_name,t3.is_black from user t1 " +
                "inner join community_user t3 on t1.id = t3.user_id " +
                "left join (select user_id, sum(score) total_score from score_history group by user_id) t2 " +
                "on t1.id = t2.user_id " +
                "where t3.community_id = ? {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} "
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
            strCondition = "and t3.level = {0}".replace("{0}", level!!)
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
            strCondition = "and t3.level > 0"
        }
        sql = sql.replace("{7}", strCondition)

        //是否为黑名单
        if (!is_black.isNullOrBlank()) {
            strCondition = "and t3.is_black={8} ".replace("{8}", is_black!!)
        }
        sql = sql.replace("{8}", strCondition)

        if (!role.isNullOrBlank()) {
            strCondition = "and t3.role = '$role'"
        }
        sql = sql.replace("{9}", strCondition)

        return create!!.resultQuery(sql, communityId).fetch()
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

    /**
     * 添加团体组织用户关联数据
     * @param communityUser: 关联数据
     */
    fun insertCommunityUser(communityUser: CommunityUser) {
        communityUserDao!!.insert(communityUser)
    }

    /**
     * 取得团体组织用户
     * @param communityId: 团体组织ID
     * @param userId: 用户ID
     * @return 单个团体组织用户
     */
    fun getCommunityUser(communityId: Int?, userId: Int?): CommunityUser? {
        var item = create!!.selectFrom(Tables.COMMUNITY_USER)
                .where(Tables.COMMUNITY_USER.COMMUNITY_ID.eq(communityId))
                .and(Tables.COMMUNITY_USER.USER_ID.eq(userId))
                .fetchOneInto(CommunityUser::class.java)
        return item
    }

    /**
     * 更新团体组织用户
     * @param communityUser: 团体组织用户信息
     */
    fun updateCommunityUser(communityUser: CommunityUser?) {
        communityUserDao!!.update(communityUser)
    }

    /**
     * 删除团体组织用户
     * @param communityId: 团体组织ID
     * @param userId: 用户ID
     */
    fun deleteCommunityUser(communityId: Int?, userId: Int) {
        create!!.execute("delete from community_user where community_id = ? and user_id = ?", communityId, userId)
    }

    /**
     * 检查当前用户是否是团体的会员
     */
    fun isVip(communityId: Int, userId: Int, year: Int): Boolean {
        var level = getVipYear(communityId, userId)
        return level == year
    }

    /**
     * 获取会员年度
     */
    fun getVipYear(communityId: Int, userId: Int): Int {
        val sql = "select level from community_user where user_id = ? and community_id = ?"
        val level = create!!.fetchOne(sql, userId, communityId)
        return if (level == null) 0 else level.get("level", Int::class.java)
    }

    /**
     * 更新VIP会员
     */
    fun updateVipYear(communityId: Int, userId: Int, year: Int) {
        var communityUser = getCommunityUser(communityId, userId)
        if (communityUser == null) {
            communityUser = CommunityUser()
            communityUser!!.userId = userId
            communityUser!!.communityId = communityId
            communityUser!!.created = DateUtil.date().toTimestamp()
        }

        if (communityUser!!.level != year) {
            communityUser!!.level = year
            if (communityUser!!.id != null && communityUser!!.id > 0) {
                updateCommunityUser(communityUser)
            } else {
                insertCommunityUser(communityUser)
            }
        }
    }


    /**
     * 移除黑名单
     */
    fun removeBlack(id: Int): Record {
        var sql = "update community_user set is_black=0 where user_id=? "
        return create!!.resultQuery(sql, id).fetchOne()
    }

    /**
     * 加入黑名单
     */
    fun addBlack(id: Int): Record {
        var sql = "update community_user set is_black=1 where user_id=? "
        return create!!.resultQuery(sql, id).fetchOne()
    }
}