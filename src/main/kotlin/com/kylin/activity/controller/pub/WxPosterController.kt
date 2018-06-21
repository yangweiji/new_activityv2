package com.kylin.activity.controller.pub

import com.kylin.activity.service.ActivityPhotoService
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
    @RequestMapping(value="/getPosters",method = [RequestMethod.GET,RequestMethod.POST])
    fun getPosters():Any{
        var posters=posterService!!.getPosterByActivityId()
        var posterItems = mutableListOf<MutableMap<String, Any?>>()
        for (poster in posters) {
            var map = mutableMapOf<String, Any?>()
            var avatar:String?=null
            if (poster["avatar"] != null) {
                avatar = commonService!!.getDownloadUrl(poster.get("avatar", String::class.java), "middle")
            }
            var mobileAvatar:String?=null
            if(mobileAvatar==null){
                mobileAvatar=avatar
            }
            if (poster["mobile_avatar"] != null) {
                mobileAvatar = commonService!!.getDownloadUrl(poster.get("mobile_avatar", String::class.java), "middle")
            }
            map["id"]=poster.get("id",Int::class.java)
            map["title"]=poster.get("title").toString()
            map["created"]=util!!.fromNow(poster.get("created"))
            map["activity_id"]=poster.get("activity_id",Int::class.java)
            map["link"]=poster.get("link").toString()
            map["avatar"]=avatar
            map["mobile_avatar"]=mobileAvatar
            map["poster_type"]=poster.get("poster_type").toString()
            map["show"]=poster.get("show",Boolean::class.java)
            map["sequence"]=poster.get("sequence",Int::class.java)
            posterItems.add(map)
        }
        return posterItems
    }
}