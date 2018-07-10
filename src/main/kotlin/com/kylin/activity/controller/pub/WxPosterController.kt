package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.Poster
import com.kylin.activity.service.PosterService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 微信海报相关控制器
 */
@RestController
@RequestMapping("pub/wx/poster")
class WxPosterController {

    /**
     * 通用服务
     */
    @Autowired
    private var commonService: CommonService? = null


    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 海报服务
     */
    @Autowired
    private val posterService: PosterService?=null

    /**
     * 获取海报信息
     */
    @CrossOrigin
    @RequestMapping(value="/getPosters",method = [RequestMethod.GET])
    fun getPosters():Any{
        var posters=posterService!!.getTopPosters()
        var posterItems = mutableListOf<MutableMap<String, Any?>>()
        for (poster in posters) {
            var map = mutableMapOf<String, Any?>()
            var mobileAvatar:String?=null
            if (poster["mobile_avatar"] != null) {
                mobileAvatar = commonService!!.getDownloadUrl(poster.get("mobile_avatar", String::class.java), "middle")
            }
            if(map["activity_id"]!=null&&map["activity_id"]!=0){
                poster.setValue(poster.fieldsRow().field("link",String::class.java),"/pub/wx/activity/details"+poster.get("activity_id"))
            }
            map["link"]=poster.get("link").toString()
            map["mobile_avatar"]=mobileAvatar
            posterItems.add(map)
        }
        return posterItems
    }

}