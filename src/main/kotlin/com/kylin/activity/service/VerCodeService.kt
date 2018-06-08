package com.kylin.activity.service


import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivitySmsDao
import com.kylin.activity.databases.tables.daos.VercodeDao
import com.kylin.activity.databases.tables.pojos.ActivitySms
import com.kylin.activity.databases.tables.pojos.Vercode
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 短信服务
 * @author Richard C. Hu
 */
@Service
class VerCodeService {

    /**
     * 短信DAO
     */
    @Autowired
    private val vercodeDao: VercodeDao? = null

    /**
     * 数据操作上下文
     */
    @Autowired
    private val create: DSLContext? = null

    /**
     * 获取用户当前的短信验证码信息
     * @param mobile: 手机号码
     * @param code: 验证码
     */
    fun getVerCode(mobile: String, code: String): Vercode? {
        var vercode = create!!.selectFrom(Tables.VERCODE)
                .where(Tables.VERCODE.MOBILE.eq(mobile).and(Tables.VERCODE.CODE.eq(code)))
                .orderBy(Tables.VERCODE.CREATED.desc())
                .fetchInto(Vercode::class.java).firstOrNull()
        return vercode
    }

    /**
     * 添加短信验证码记录
     * @param verCode: 验证码信息
     */
    fun insert(verCode: Vercode) {
        vercodeDao!!.insert(verCode)
    }
}