package com.kylin.activity.service

import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.AuthUser
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


/**
 * Created by stephan on 20.03.16.
 */
@Service
class AuthUserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private val create: DSLContext? = null

    @Override
    override fun loadUserByUsername(username: String): UserDetails? {

        var user = create!!.selectFrom(Tables.USER)
                .where(Tables.USER.USERNAME.eq(username))
                .fetchOneInto(User::class.java)
        return if (user != null) AuthUser(user) else null
    }

    fun loadUserByUnionId(unionId: String): UserDetails? {

        var user = create!!.selectFrom(Tables.USER)
                .where(Tables.USER.UNION_ID.eq(unionId))
                .fetchOneInto(User::class.java)
        //设定用户名
        user.username = unionId
        return if (user != null) AuthUser(user) else null
    }
}
