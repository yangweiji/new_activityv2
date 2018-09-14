package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * Created by 9kylin on 2018-06-12.
 */
@RestController
@RequestMapping("pub/wx/profile")
class WxProfileController {
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 个人信息服务
     */
    @Autowired
    private val proFileService: ProfileService? = null

    /**
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null

    /**
     * 公共服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 团体服务
     */
    @Autowired
    private val communityService: CommunityService? = null

    /**
     * 团体用户服务
     */
    @Autowired
    private val thirdUserService: ThirdUserService? = null

    /**
     * 团体积分服务
     */
    @Autowired
    private val thirdScoreService: ThirdScoreService? = null


    /**
     * 小程序个人中心积分信息
     * @param userId 用户ID
     * @param communityId 团体ID
     */
    @GetMapping("/scores")
    fun getScores(@RequestParam(required = false) userId: Int?, @RequestParam(required = false) communityId: Int?): Any {
        val scores = proFileService!!.getActivityIntegral(communityId, userId)
        return scores.intoMaps()
    }


    /**
     * 完善个人信息
     * @param userId 用户id
     */
    @GetMapping("/getuserinfo")
    fun getUserInfo(@RequestParam(required = false) userId: Int): User {
        return userService!!.getUser(userId)
    }

    /**
     * 保存并更新用户信息
     * @param user 用户信息
     */
    @Transactional
    @PostMapping("/saveuserinfo")
    fun saveUserInfo(@RequestBody user: User): User {
        var userInfo = userService!!.getUser(user.id)
        userInfo!!.displayname = user.displayname
        userInfo!!.gender = user.gender
        userInfo!!.email = user.email
        userInfo!!.bloodType = user.bloodType
        userInfo!!.clothingSize = user.clothingSize
        userInfo!!.workCompany = user.workCompany
        userInfo!!.occupation = user.occupation
        userInfo!!.emergencyContactName = user.emergencyContactName
        userInfo!!.emergencyContactMobile = user.emergencyContactMobile
        userInfo!!.isParty = user.isParty
        userInfo!!.address = user.address
        userInfo!!.wechatId = user.wechatId
        proFileService!!.updateUserInfo(userInfo)
        return userInfo
    }


    /**
     * 完成实名认证，同时更新用户信息
     * @param user 用户信息
     */
    @Transactional
    @PostMapping("/saverealinfo")
    fun saveRealInfo(@RequestBody user: User): User {
        var userInfo = userService!!.getUser(user.id)

        userInfo!!.realName = user.realName
        userInfo!!.idCard = user.idCard
        userInfo!!.realTime = DateUtil.date().toTimestamp()
        userInfo!!.isReal = true

        userInfo!!.displayname = user.displayname
        userInfo!!.gender = user.gender
        userInfo!!.email = user.email
        userInfo!!.bloodType = user.bloodType
        userInfo!!.clothingSize = user.clothingSize
        userInfo!!.workCompany = user.workCompany
        userInfo!!.occupation = user.occupation
        userInfo!!.emergencyContactName = user.emergencyContactName
        userInfo!!.emergencyContactMobile = user.emergencyContactMobile
        userInfo!!.isParty = user.isParty
        userInfo!!.address = user.address
        userInfo!!.wechatId = user.wechatId
        proFileService!!.updateUserInfo(userInfo)
        return userInfo
    }


    /**
     * 个人中心我的活动
     * @param type 活动类型
     * @param userId 活动编号
     * @param communityId 编号id
     *
     */
    @CrossOrigin
    @GetMapping("/getmyactivities")
    fun myActivities(@RequestParam(required = false) type: Int?, userId: Int?, communityId: Int?): List<Any> {
        var activitiesList = proFileService!!.myActivities(type, userId, communityId)
        var items = mutableListOf<MutableMap<String, Any?>>()
        for (activity in activitiesList) {
            var map = mutableMapOf<String, Any?>()
            var avatar: String? = null
            if (activity["avatar"] != null) {
                avatar = commonService!!.getDownloadUrl(activity.get("avatar", String::class.java), "middle")
            }
            map["id"] = activity.get("id", Int::class.java)
            map["community_id"] = activity.get("community_id", Int::class.java)
            map["title"] = activity.get("title").toString()
            map["avatar"] = avatar
            map["start_time"] = util!!.fromNow(activity.get("start_time"))
            map["activity_type"] = activity.get("activity_type", Int::class.java)
            map["attend_count"] = activity.get("attend_count", Int::class.java)
            map["favorite_count"] = activity.get("favorite_count", Int::class.java)

            items.add(map)
        }
        return items
    }

    /**
     * 个人信息页相关数据
     * @param userId 用户编id
     * @param communityId 团体id
     */
    @GetMapping("/info")
    fun getProfileInfo(@RequestParam(required = false) userId: Int,
                       @RequestParam(required = false) communityId: Int): Any {

        var result = mutableMapOf<String, Any>()
        var user = userService!!.getUser(userId)
        result["user"] = user
        var community = communityService!!.getCommunity(communityId)
        result["community"] = community
        result["vipYear"] = thirdUserService!!.getVipYear(communityId, userId)
        result["score"] = thirdScoreService!!.getUseableScore(userId, communityId)
        var personalInfoCounts = proFileService!!.getPersonalInfoCounts(userId, communityId)
        var counts = personalInfoCounts.intoMaps()
        result["activityCounts"] = counts
        return result
    }


    /**
     * vip页，查询用户在指定团体的vip信息
     * @param userId 用户编id
     * @param communityId 团体id
     */
    @GetMapping("/vip")
    fun getVipInfo(@RequestParam(required = false) userId: Int,
                   @RequestParam(required = false) communityId: Int): Any {

        var result = mutableMapOf<String, Any?>()
        var user = userService!!.getUser(userId)
        result["user"] = user
        var community = communityService!!.getCommunity(communityId)
        result["community"] = community
        result["vipYear"] = thirdUserService!!.getVipYear(communityId, userId)

        //取得团体用户信息
        //有可能为null值，新用户注册后认证成为会员，没有加入任何团体的情况
        result["communityUser"] = thirdUserService!!.getCommunityUser(communityId, userId)

        return result
    }

    /**
     * 更新vip年度信息
     */
    @GetMapping("/updatevip")
    fun updateVipYear(@RequestParam(required = false) userId: Int,
                      @RequestParam(required = false) communityId: Int, @RequestParam(required = false) year: Int) {
        thirdUserService!!.updateVipYear(communityId, userId, year)
    }

    /**
     * VIP会员查看条款页面内容
     */
    @GetMapping("/agreement")
    fun getVipAgreement(@RequestParam(required = false) communityId: Int): Community {
        return communityService!!.getCommunity(communityId)
    }


}