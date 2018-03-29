package com.kylin.activity.controller.pub

import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("pub/file")
class FileRestController{

    @Autowired
    private val commonService: CommonService? = null

    @RequestMapping("/policy")
     fun getPolicy():LinkedHashMap<String, String> {

        var respMap = commonService!!.getPolicy()
        return respMap
    }
}