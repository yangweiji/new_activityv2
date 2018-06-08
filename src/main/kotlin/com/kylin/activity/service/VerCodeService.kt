package com.kylin.activity.service


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
     * 获取用户当前的短信验证码
     * @param mobile: 手机号码
     */
    fun getVerCode(mobile: String): String? {
        var sql = "select code from vercode order by created desc limit 1"
        var code = create!!.fetchValue(sql)

        return if (code != null) code.toString() else null
    }

    /**
     * 添加短信验证码记录
     * @param verCode: 验证码信息
     */
    fun insert(verCode: Vercode) {
        vercodeDao!!.insert(verCode)
    }
}