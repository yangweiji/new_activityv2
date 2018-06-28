package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.*
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
     * 用户服务
     */
    @Autowired
    private val userService: UserService? = null


    /**
     * 微信端个人信息页面初始化
     * @param userId 用户ID
     * @param communityId 团体ID
     */
    @GetMapping("/getIntegral")
    fun getIntegral(@RequestParam(required = false) userId: Int?, @RequestParam(required = false) communityId: Int?): List<Any> {
        val scores = proFileService!!.getActivityIntegral(communityId, userId)
        return scores.intoMaps()
    }


    /**
     * 用户已参加活动数
     */
    @CrossOrigin
    @GetMapping("/getActivityAttendCounts")
    fun activityAttendCounts(@RequestParam(required = false) userId: Int?,
                             @RequestParam(required = false) communityId: Int?): InfoData {
        return proFileService!!.activityAttendCounts(userId, communityId)
    }


    /**
     * 用户收藏活动数
     */
    @CrossOrigin
    @GetMapping("/getFavoriteActivityCounts")
    fun favoriteActivityCounts(@RequestParam(required = false) userId: Int?,
                               @RequestParam(required = false) communityId: Int?): InfoData {
        return proFileService!!.favoriteActivityCounts(userId, communityId)
    }


    /**
     * 用户需签到活动数
     *
     */
    @CrossOrigin
    @GetMapping("/getNoCheckedActivityCounts")
    fun noCheckedActivityCounts(@RequestParam(required = false) userId: Int?,
                                @RequestParam(required = false) communityId: Int?): InfoData {
        return proFileService!!.noCheckedActivityCounts(userId, communityId)
    }


    /**
     * 用户已签到活动数
     */
    @CrossOrigin
    @GetMapping("/getCheckedActivityCounts")
    fun checkedActivityCounts(@RequestParam(required = false) userId: Int?,
                              @RequestParam(required = false) communityId: Int?): InfoData {
        return proFileService!!.checkedActivityCounts(userId, communityId)
    }


    /**
     * 用户积分总额
     */
    @CrossOrigin
    @GetMapping("/getSumScores")
    fun sumScores(@RequestParam(required = false) userId: Int?,
                  @RequestParam(required = false) communityId: Int?): InfoData {
        return proFileService!!.sumScores(userId, communityId)
    }


    /**
     * 完善个人信息
     */
    @CrossOrigin
    @GetMapping("/getIntoPersonalInformation")
    fun intoPersonalInformation(@RequestParam(required = false) userId: Int): User? {
        var user = userService!!.getUser(userId)
        return user
    }


    /**
     * 保存并更新用户信息
     */
    @CrossOrigin
    @Transactional
    @PostMapping("/savePersonalInformation")
    fun savePersonalInformation(user: User): Boolean {
        var userInfo = userService!!.getCurrentUserInfo()
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
        return true
    }



}