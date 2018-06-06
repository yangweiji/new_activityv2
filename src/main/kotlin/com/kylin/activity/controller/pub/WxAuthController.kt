package com.kylin.activity.controller.pub

import com.kylin.activity.config.WxMaProperties
import com.kylin.activity.databases.tables.Activity
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ArticleService
import com.kylin.activity.service.WxService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 微信活动相关控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/auth")
class WxAuthController {


    @Autowired
    private val wxService: WxService? = null

    /**
     * 查询指定文章分类的文章信息
     * @param clientId: 团体组织标识
     * @param category: 文章分类
     * @return 文章信息集合
     */
    @GetMapping("/getSessionInfo")
    fun getSessionInfo(@RequestParam(required = false) code: String?): Any {

        return wxService!!.maService!!.userService!!.getSessionInfo(code)
    }


}