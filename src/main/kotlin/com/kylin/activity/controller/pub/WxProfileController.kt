package com.kylin.activity.controller.pub

import com.kylin.activity.service.*
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
    private val proFileService: ProfileService? = null

    @Autowired
    private val thirdUserService: ThirdUserService? = null

    /**
     * 活动服务
     */
    private val activityService: ActivityService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 团体服务
     */
    @Autowired
    private val communityService: CommunityService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 微信端个人信息页面初始化
     */
    @GetMapping("/getPerInformation")
    fun getPerInformation(@RequestParam(required = false)userId:Int?,@RequestParam(required = false)communityId:Int?): Any {
         /*val currentActivity = activityService!!.getActivityDetail(activityId)*/
         var community = communityService!!.getCommunity(communityId!!)

        var proInformation = proFileService!!.getInitProInformation(communityId,userId)

        var mapList = mutableListOf<MutableMap<String, Any?>>()
        for (item in proInformation) {
            var map = mutableMapOf<String, Any?>()
            var userAvatar: String? = null
            var displayname: String? = null
            var username: String? = null
            if (item["displayname"] != null) {
                displayname = item.get("displayname").toString()
            }

            if (item["username"] != null) {
                username = item.get("username").toString()
            }
            if (item["avatar"] != null) {
                userAvatar = commonService!!.getDownloadUrl(item.get("avatar", String::class.java), "middle")
            }
            map["id"] = item.get("id", Int::class.java)
            map["displayname"] = displayname
            map["username"] = username
            map["avatar"] = userAvatar

            /* val isVip = thirdUserService!!.isVip(community!!.id, userId!!, DateUtil.thisYear())*/
            /* if(isVip){
                 map["level"]=isVip
             }*/

            //会员认证
           /* var isVip = item.get("level", Int::class.java) == DateUtil.thisYear()
            map["level"] = isVip*/
              /*  map["level"]=item.get("level", Int::class.java)==DateUtil.thisYear()*/

            //是否为本年的VIP
            val isVip = proFileService!!.isVip(community!!.id, userId!!, DateUtil.thisYear())
            if(isVip) map["level"]=isVip
            //实名认证
            map["is_real"] = item.get("is_real", Boolean::class.java)
            if (map["is_real"] == null) 0 else map["is_real"]

            //已参加
            map["attend_user_count"] = item.get("attend_user_count", Int::class.java)
            //我喜欢
            map["favorite_count"] = item.get("favorite_count", Int::class.java)
            //需签到
            map["ne_checked_count"] = item.get("ne_checked_count", Int::class.java)
            //已签到
            map["checked_count"] = item.get("checked_count", Int::class.java)
            mapList.add(map)
        }
        return mapList
    }
}