package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.daos.VercodeDao
import com.kylin.activity.databases.tables.pojos.Vercode
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.time.Instant
import java.util.Random
import org.jooq.DSLContext



@RestController
@RequestMapping("pub/vercode")
class VercodeRestController {

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val dsl: DSLContext? = null

    @Autowired
    private val vercodeDao:VercodeDao? = null


    @RequestMapping("/register/{mobile}")
    fun register(@PathVariable mobile:String): Boolean {
        val random = Random()
        var code = (random.nextInt(900000) + 100000).toString()
        commonService!!.sendSms(mobile, code)
        var vercode = Vercode()

        vercode.mobile = mobile
        vercode.created = Timestamp.from(Instant.now())
        vercode.code = code
        //dsl!!.executeInsert(vercode)
        //var vercodeDao = VercodeDao(dsl!!.configuration())
        vercodeDao!!.insert(vercode)
        return true
    }
}