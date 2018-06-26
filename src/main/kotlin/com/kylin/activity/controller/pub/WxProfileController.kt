package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * Created by 9kylin on 2018-06-12.
 */
@RestController
@RequestMapping("pub/wx/profile")
class WxProfileController {
    /**
     * 个人信息服务
     */
    @Autowired
    private val proFileService: ProfileService? = null

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
     * 用户服务
     */
    @Autowired
    private val userService:UserService?=null

    /**
     * 微信端个人信息页面初始化
     * @param userId 用户ID
     * @param communityId 团体ID
     */
    @GetMapping("/getPerInformation")
    fun getPerInformation(@RequestParam(required = false) userId: Int?, @RequestParam(required = false) communityId: Int?): Any {
        var community = communityService!!.getCommunity(communityId!!)

        var proInformation = proFileService!!.getInitProInformation(communityId, userId)

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

            //是否为本年的VIP
            val isVip = proFileService!!.isVip(community!!.id, userId!!, DateUtil.thisYear())
            if (isVip) map["level"] = isVip
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
            //积分总额
            map["sum_score"] = item.get("sum_score", Int::class.java)
            mapList.add(map)

        }
        return mapList
    }


    /**
     * 小程序：完善个人信息页面
     * @param userId 用户id
     *
     */
    @CrossOrigin
    @GetMapping("/getIntoPersonalInformation")
    fun intoPersonalInformation(@RequestParam(required = false) userId: Int?): User {
        return userService!!.getUser(userId!!)
    }


    /**
     * 小程序：更新并保存用户信息
     */
    @CrossOrigin
    @Transactional
    @GetMapping("/savePersonalInformation")
    fun savePersonalInformation(displayname: String?, email: String?, gender: Int?,
                                bloodType: String?, clothingSize: String?, workCompany: String?, occupation: String?,
                                emergencyContactName: String?, emergencyContactMobile: String?, isParty: Boolean?,
                                address: String?, wechatId: String?):Boolean {
        var userInfo=proFileService!!.fetchByUsername()
        userInfo.displayname=displayname
        userInfo.gender=gender
        userInfo.email=email
        userInfo.bloodType=bloodType
        userInfo.clothingSize=clothingSize
        userInfo.workCompany=workCompany
        userInfo.occupation=occupation
        userInfo.emergencyContactName=emergencyContactName
        userInfo.emergencyContactMobile=emergencyContactMobile
        userInfo.isParty=isParty
        userInfo.address=address
        userInfo.wechatId=wechatId
        proFileService!!.updateUserInfo(userInfo)
        return true
    }
}