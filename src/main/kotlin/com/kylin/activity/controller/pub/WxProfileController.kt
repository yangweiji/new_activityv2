package com.kylin.activity.controller.pub

import com.kylin.activity.service.ProfileService
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by 9kylin on 2018-06-12.
 */
@RestController
@RequestMapping("pub/wx/profile")
class WxProfileController {
@Autowired
private val proFileService:ProfileService?=null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 微信端个人信息页面初始化
     */
     @GetMapping("/getPerInformation")
    fun getPerInformation(@RequestParam(required = false)userId:Int?):Any{
       var proInformation=proFileService!!.getInitProInformation(userId)
       var mapList= mutableListOf<MutableMap<String,Any?>>()
        for(item in proInformation){
            var map= mutableMapOf<String,Any?>()
            var userAvatar:String?=null
            var displayname:String?=null
            var username:String?=null
            if(item["displayname"]!=null){
                displayname=item.get("displayname").toString()
            }

            if(item["username"]!=null){
                username=item.get("username").toString()
            }
            if(item["avatar"]!=null){
                userAvatar=commonService!!.getDownloadUrl(item.get("avatar",String::class.java),"middle")
            }
            map["user_id"]=item.get("user_id",Int::class.java)
            map["displayname"]=displayname
            map["username"]=username
            map["avatar"]=userAvatar
            map["level"]=DateUtil.thisYear()
            map["is_real"]=item.get("is_real",Boolean::class.java)
            map["counts"]=item.get("counts",Int::class.java)
            mapList.add(map)
        }
        return mapList
    }
}