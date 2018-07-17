package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.CommunityUserDao
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.databases.tables.pojos.Vercode
import com.kylin.activity.model.AuthUser
import com.kylin.activity.sms.SmsTemplateListProperties
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService {

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val smsTemplateListProperties: SmsTemplateListProperties? = null

    @Autowired
    private val userDao: UserDao? = null

    @Autowired
    private val communityUserDao: CommunityUserDao? = null

    @Autowired
    private val create: DSLContext? = null

    /**
     * 短信验证码服务
     */
    @Autowired
    private val verCodeService: VerCodeService? = null

    fun getRoles(): Map<String, String> {
        val roles = mapOf<String,String>("ADMIN" to "管理员", "PUBLISH" to "发布者")
        return roles
    }

    /**
     * 注册用户
     */
    fun register(username: String, password: String, code: String): String {
        var vercode = create!!.selectFrom(Tables.VERCODE)
                .where(Tables.VERCODE.MOBILE.eq(username).and(Tables.VERCODE.CODE.eq(code)))
                .orderBy(Tables.VERCODE.CREATED.desc())
                .fetchInto(Vercode::class.java).firstOrNull()
        if (vercode != null && DateUtil.betweenMs(DateUtil.date(), vercode.created) <= smsTemplateListProperties!!.timeout) {
            var users = userDao!!.fetchByUsername(username)
            return if (users != null && users.count() > 0) {
                "exist"  //用户已经存在
            } else {
                var user = User()
                user.username = username
                user.mobile = username
                var coder = BCryptPasswordEncoder()
                user.password = coder.encode(password)
                user.enabled = true
                user.created = DateUtil.date().toTimestamp()
                user.displayname = username

                userDao!!.insert(user)
                "success" // 用户注册成功
            }
        } else {
            return "vercode" // 验证码错误或过期
        }
    }

    /**
     * 微信小程序注册用户
     */
    fun register(username: String
                 , password: String
                 , openId: String
                 , code: String
                 , nickName: String
                 , avatarUrl: String): String {
        var vercode = create!!.selectFrom(Tables.VERCODE)
                .where(Tables.VERCODE.MOBILE.eq(username).and(Tables.VERCODE.CODE.eq(code)))
                .orderBy(Tables.VERCODE.CREATED.desc())
                .fetchInto(Vercode::class.java).firstOrNull()

        //注册，短信有效期10分钟
        return if (vercode != null && DateUtil.betweenMs(DateUtil.date(), vercode.created) <= 1000 * 60 * 10) {
            var users = userDao!!.fetchByUsername(username)

            if (users != null && users.count() > 0) {
                "exist"  //用户已经存在
            } else {
                var user = User()
                user.username = username
                user.mobile = username
                var coder = BCryptPasswordEncoder()
                user.password = coder.encode(password)
                user.enabled = true
                user.created = DateUtil.date().toTimestamp()
                user.displayname = nickName
                user.nickName = nickName
                user.avatar = avatarUrl
                user.openId = openId

                userDao!!.insert(user)
                LogUtil.printLog("用户注册成功, ID: ${user.id}")
                "success" // 用户注册成功
            }
        } else {
            "vercode" // 验证码错误或过期
        }
    }

    /**
     * 修改密码
     */
    fun changePassword(username: String, password: String, code: String): String {
        var vercode = create!!.selectFrom(Tables.VERCODE)
                .where(Tables.VERCODE.MOBILE.eq(username).and(Tables.VERCODE.CODE.eq(code)))
                .orderBy(Tables.VERCODE.CREATED.desc())
                .fetchInto(Vercode::class.java).firstOrNull()
        if (vercode != null && DateUtil.betweenMs(DateUtil.date(), vercode.created) <= 1000 * 60) {
            var users = userDao!!.fetchByUsername(username)
            if (users == null || users.count() == 0) {
                return "noexist"  //用户不存在存在
            } else {
                var user = users[0]

                var coder = BCryptPasswordEncoder()
                user.password = coder.encode(password)
                user.enabled = true

                userDao!!.update(user)
                return "success" // 用户注册成功
            }
        } else {
            return "vercode" // 验证吗错误或过期
        }
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
     * 取得用户信息
     */
    fun getUser(id: Int): User {
        var user = userDao!!.findById(id)
        return user
    }

    /**
     * 取得用户信息
     */
    fun getUser(username: String): User? {
        return userDao!!.fetchOne(Tables.USER.USERNAME, username)
    }

    /**
     * 依据用户名和密码取得用户信息
     * @param username: 手机号
     * @param password: 加密密码
     * @return 用户信息
     */
    fun getUser(username: String, password: String): User? {
        return create!!.selectFrom(Tables.USER)
                .where(Tables.USER.USERNAME.eq(username))
                .and(Tables.USER.PASSWORD.eq(password))
                .fetchOneInto(User::class.java)
    }

    /**
     * 全部用户
     */
    fun getAllUsers(): List<User> {
        var items = userDao!!.findAll()
        return items
    }

    /**
     * 查询全部用户与积分
     */
    fun getAllUsersAndScores(start: String?, end: String?, username: String?, displayname: String?, real_name: String?, id_card: String?): Result<Record> {
        var sql = "select t1.*, t2.total_score from user t1 " +
                "left join (select user_id, sum(score) total_score " +
                "from score_history " +
                "group by user_id) t2 " +
                "on t1.id = t2.user_id "+
        "where 1=1 {0} {1} {2} {3} {4} {5} "
        var strCondition = ""
        if (!username.isNullOrBlank())
        {
            strCondition = "and t1.username like '%{0}%'".replace("{0}", username!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!displayname.isNullOrBlank())
        {
            strCondition = "and t1.displayname like '%{0}%'".replace("{0}", displayname!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank())
        {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!id_card.isNullOrBlank())
        {
            strCondition = "and t1.id_card like '%{0}%'".replace("{0}", id_card!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!start.isNullOrBlank())
        {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{4}", strCondition)

        if (!end.isNullOrBlank())
        {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{5}", strCondition)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }

    /**
     * 认证会员
     */
    fun getMembers(): List<User> {
        var items = create!!.selectFrom(Tables.USER).where(Tables.USER.LEVEL.greaterThan(0)).fetch().map(userDao!!.mapper())
        return items
    }

    /**
     * 认证会员
     */
    fun getMembers(start: String?, end: String?, username: String?, displayname: String?,real_name: String?,id_card: String?,level: String?): Result<Record> {

        var sql = "select t1.*, t2.total_score from user t1 " +
                "left join (select user_id, sum(score) total_score " +
                "from score_history " +
                "group by user_id) t2 " +
                "on t1.id = t2.user_id " +
                "where t1.level > 0 {0} {1} {2} {3} {4} {5} {6} "
        var strCondition = ""
        if (!username.isNullOrBlank())
        {
            strCondition = "and t1.username like '%{0}%'".replace("{0}", username!!)
        }
        sql = sql.replace("{0}", strCondition)

        if (!displayname.isNullOrBlank())
        {
            strCondition = "and t1.displayname like '%{0}%'".replace("{0}", displayname!!)
        }
        sql = sql.replace("{1}", strCondition)

        if (!real_name.isNullOrBlank())
        {
            strCondition = "and t1.real_name like '%{0}%'".replace("{0}", real_name!!)
        }
        sql = sql.replace("{2}", strCondition)

        if (!id_card.isNullOrBlank())
        {
            strCondition = "and t1.id_card like '%{0}%'".replace("{0}", id_card!!)
        }
        sql = sql.replace("{3}", strCondition)

        if (!level.isNullOrBlank())
        {
            strCondition = "and t1.level = {0}".replace("{0}", level!!)
        }
        sql = sql.replace("{4}", strCondition)

        if (!start.isNullOrBlank())
        {
            strCondition = "and date(t1.created) >= '{0}'".replace("{0}", start!!)
        }
        sql = sql.replace("{5}", strCondition)

        if (!end.isNullOrBlank())
        {
            strCondition = "and date(t1.created) <= '{0}'".replace("{0}", end!!)
        }
        sql = sql.replace("{6}", strCondition)

        var items = create!!.resultQuery(sql).fetch()
        return items
    }


    /**
     * 更新用户
     */
    fun update(user: User) {
        userDao!!.update(user)
    }

    fun checkPermission(role:String):Boolean{
        var user = getCurrentUserInfo()
        var roles = getRoles()
        return user!!.role == roles.get("ADMIN") || user!!.role == roles.get(role)
    }

    fun insert(user: User) {
        userDao!!.insert(user)
    }

    fun deleteById(id: Int) {
        userDao!!.deleteById(id)
    }

    /**
     * 取得团体组织的管理员，唯一的管理员用户
     * @param id: 团体组织ID
     * @return 团体组织管理员
     */
    fun getCommunityUser(id: Int?): User? {
        var sql = "select t1.* from user t1 " +
                "inner join community_user t2 on t1.id = t2.user_id " +
                "where t2.role = '管理员' and t2.community_id = ?"
        var items = create!!.resultQuery(sql, id).fetch()
        if (items.size > 0)
            return items.first().into(User::class.java)
        return null
    }

    /**
     * 添加团体组织用户关联对象信息
     * @param communityUser: 团体组织用户关联对象
     * @return 团体组织用户关联对象ID
     */
    fun insertCommunityUser(communityUser: CommunityUser): Int {
        communityUserDao!!.insert(communityUser)
        return communityUser.id
    }

    /**
     * 更新团体组织用户关联对象信息
     * @param communityUser: 团体组织用户关联对象
     * @return 团体组织用户关联对象ID
     */
    fun updateCommunityUser(communityUser: CommunityUser): Int {
        communityUserDao!!.update(communityUser)
        return communityUser.id
    }

    /**
     * 依据openId取得唯一的用户
     * @return 用户信息
     */
    fun getUserByOpenId(openId: String): User? {
        return create!!.selectFrom(Tables.USER)
                .where(Tables.USER.OPEN_ID.eq(openId))
                .fetchOneInto(User::class.java)
    }

    /**
     * 依据openId 或 unionId 取得唯一的用户， unionId优先
     * @return 用户信息
     */
    fun getUserByOpenOrUnionId(openId: String?, unionId:String?):User? {
        return if(unionId.isNullOrBlank()){
            create!!.selectFrom(Tables.USER)
                    .where(Tables.USER.OPEN_ID.eq(openId))
                    .fetchOneInto(User::class.java)

        } else {
            create!!.selectFrom(Tables.USER)
                    .where(Tables.USER.UNION_ID.eq(unionId))
                    .fetchOneInto(User::class.java)
        }
    }

    /**
     * 依据unionId取得唯一的用户
     * @return 用户信息
     */
    fun getUserByUnionId(unionId: String): User? {
        return create!!.selectFrom(Tables.USER)
                .where(Tables.USER.UNION_ID.eq(unionId))
                .fetchOneInto(User::class.java)
    }

    /**
     * 添加手机号码
     */
    fun addMobile(id: Int, mobile: String, password: String): Boolean {
        var user = this.getUser(id)

        if (user != null) {
            var coder = BCryptPasswordEncoder()
            user.username = mobile
            user.password = coder.encode(password)
            user.mobile = mobile
            user.enabled = true

            this.update(user)
            return true
        }

        return false
    }

    /**
     * 更换手机号码
     */
    fun updateMobile(id: Int, mobile: String, smsCode: String, newMobile: String, smsCode2: String): Boolean {
        var user = this.getUser(id)
        var verCode = verCodeService!!.getVerCode(mobile, smsCode)
        var verCode2 = verCodeService!!.getVerCode(newMobile, smsCode2)

        if (verCode != null && verCode2 != null) {
            user!!.username = newMobile
            user!!.mobile = newMobile
            user!!.enabled = true
            this.update(user)
            return true
        }

        return false
    }
}