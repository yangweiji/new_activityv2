package com.kylin.activity.controller.pub

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kylin.activity.databases.tables.Activity
import com.kylin.activity.databases.tables.pojos.ActivityUser
import com.kylin.activity.databases.tables.pojos.ScoreHistory
import com.kylin.activity.model.ActivityAttendInfo
import com.kylin.activity.model.ActivityScoreInfo
import com.kylin.activity.service.*
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import com.xiaoleilu.hutool.date.DateUnit
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 微信活动相关控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/activity")
class WxActivityController {

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

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
     * 团体积分服务
     */
    @Autowired
    private val thirdScoreService: ThirdScoreService? = null

    /**
     * 团体用户服务
     */
    @Autowired
    private val thirdUserService: ThirdUserService? = null

    /**
     * 积分服务
     */
    @Autowired
    private val scoreService: ScoreService? = null

    /**
     * 上下文环境信息，可读取配置文件
     */
    @Autowired
    private val env: Environment? = null

    /**
     * 数据访问上下文
     */
    @Autowired
    private val create: DSLContext? = null


    /**
     * 显示活动详情内容
     * @param activityId: 活动ID
     * @return 单个活动信息
     */
    @GetMapping("/details")
    fun details(@RequestParam(required = false) activityId: Int?): Any {
        //活动详情信息
        var activity = activityService!!.getActivityDetail(activityId)
        var avatar: String?
        if (activity["avatar"] != null) {
            avatar = commonService!!.getDownloadUrl(activity.get("avatar", String::class.java))
            activity.setValue(Activity.ACTIVITY.AVATAR, avatar)
        }

        var map = activity.intoMap()
        if (map["created"] != null) {
            map["created"] = util!!.fromNow(activity.get("start_time"))
        }

        return map
    }

    /**
     * 显示活动参与人员列表
     * @param activityId: 活动ID
     * @return 参与人员列表
     */
    @GetMapping("/attendusers")
    fun getAttendusers(@RequestParam(required = false) activityId: Int): Any {
        //活动详情信息
        var tickets =activityService!!.getActivityTickets(activityId)
        var users = activityService!!.getAttendUsers(activityId)
        return mapOf( "tickets" to tickets, "users" to users )
    }

    /**
     * 设置喜欢活动，已经喜欢的活动不在重复添加
     * @param activityId: 活动ID userId: 用户Id
     * @return 单个活动信息
     */
    @GetMapping("/favorite")
    fun favorite(@RequestParam(required = true) activityId: Int, @RequestParam(required = true) userId: Int): Int {
        return activityService!!.favorite(activityId, userId)
    }

    /**
     * 活动报名页面，检查当前用户是否报名，返回活动门票和报名相关信息
     */
    @GetMapping("/attend")
    fun getAttend(request: HttpServletRequest, @RequestParam(required = false) activityId: Int?, @RequestParam(required = false) userId: Int?): Any {

        val map = mutableMapOf<String, Any?>()

        val user = userService!!.getUser(userId!!)
        val currentActivity = activityService!!.getActivityDetail(activityId)
        var community = communityService!!.getCommunity(currentActivity!!.get("community_id", Int::class.java))
        map["user"] = user
        map["userScore"] = thirdScoreService!!.getUseableScore(userId, community!!.id)
        map["scoreRate"] = env!!.getProperty("activity.score.rate", Float::class.java)


        //是否为本年的VIP
        val isVip = thirdUserService!!.isVip(community!!.id, userId!!, DateUtil.thisYear())


        var attendUserSql = "select t1.*, t2.title ticket_title, t2.memo ticket_memo, t2.price ticket_price, count ticket_count, user_level ticket_user_level from activity_user t1 inner join activity_ticket t2 on t1.activity_ticket_id = t2.id and t1.activity_id = ? and t1.user_id=?"

        var attendUser = create!!.resultQuery(attendUserSql, activityId, user.id).fetchOne()

        map["attendUser"] = if (attendUser == null) null else attendUser.intoMap()

        map["activity"] = activityService!!.getActivityItem(currentActivity)

        val mapper = jacksonObjectMapper()


        //自动填充报名信息
        var attendInfos = mapper.readValue<List<ActivityAttendInfo>>(currentActivity.get("attend_infos", String::class.java))

        for(attendInfo in attendInfos){
            attendInfo.value = when {
                attendInfo.title == "昵称" -> user.displayname
                attendInfo.title == "邮件" -> user.email
                attendInfo.title == "性别" -> if(user.gender == 2) "女" else "男"
                attendInfo.title == "血型" -> user.bloodType
                attendInfo.title == "T恤尺寸" -> user.clothingSize
                attendInfo.title == "工作单位" -> user.workCompany
                attendInfo.title == "职业" -> user.occupation
                attendInfo.title == "紧急联系人姓名" -> user.emergencyContactName
                attendInfo.title == "紧急联系人电话" -> user.emergencyContactMobile
                attendInfo.title == "是否党员" -> if(user.isParty != null && user.isParty) "党员" else "群众"
                attendInfo.title == "家庭地址" -> user.address
                attendInfo.title == "微信号" -> user.wechatId
                attendInfo.title == "身份证号" -> user.idCard
                else -> null
            }
        }
        attendInfos[0].value = user.realName
        attendInfos[1].value = user.username

        map["attendInfos"] = attendInfos

        var checkInScore = 0

        var scoreInfos = currentActivity.get("score_infos", String::class.java)

        //积分初始化
        if (!scoreInfos.isNullOrBlank()) {
            var scoreInfo = mapper.readValue<ActivityScoreInfo>(scoreInfos)
            checkInScore = if (isVip) {
                scoreInfo.vipUserScore
            } else {
                scoreInfo.generalUserScore
            }
        }

        map["checkInScore"] = checkInScore

        var cancelMessage: String? = null
        //还未报名
        if (attendUser == null) {
            var ticketSql = "select t1.*, ifnull(t2.attend_count, 0) attend_count from activity_ticket t1 left join \n" +
                    "( select activity_ticket_id, count(user_id) attend_count from activity_user where activity_id = ? group by activity_ticket_id ) t2\n" +
                    "  on t1.id = t2.activity_ticket_id\n" +
                    "  where  t1.activity_id = ?"
            var tickets = create!!.resultQuery(ticketSql, activityId, activityId).fetch()
            var ticketInfos = mutableListOf<Any>()
            var hasTickets = false
            for (ticket in tickets) {
                var ticketMap = mutableMapOf<String, Any>()
                var userLevel = if (ticket.get("user_level") == null) 0 else ticket.get("user_level", Int::class.java)
                var maxUsers = ticket.get("count", Int::class.java)
                var attendCount = ticket.get("attend_count", Int::class.java)


                var disabled = (userLevel > 0 && !isVip) || (maxUsers > 0 && attendCount >= maxUsers)
                ticketMap["disabled"] = disabled
                if (!hasTickets && !disabled) {
                    hasTickets = true
                }
                var title = ""
                if (userLevel > 0) {
                    title += "【马协会员专享】"
                }
                if (maxUsers > 0 && attendCount >= maxUsers) {
                    title += "【满员】"
                }
                title += ticket.get("title", String::class.java)

                var price = ticket.get("price", Double::class.java)
                title += if (price > 0) {
                    "【¥ %.2f】".format(price)
                } else {
                    "【免费】"
                }
                ticketMap["title"] = title

                ticketMap["price"] = price

                var score = ticket["score"]

                if (score == null) {
                    score = ""
                }
                ticketMap["score"] = score

                ticketMap["id"] = ticket.get("id")
                ticketInfos.add(ticketMap)
            }
            map["hasTickets"] = hasTickets
            map["ticketInfos"] = ticketInfos

        } else {
            //已报名，填充报名信息
            var title = attendUser.get("ticket_title", String::class.java)
            var level = attendUser.get("ticket_user_level", Int::class.java)
            if (level > 0) {
                title += "【马协会员专享】"
            }
            var price = attendUser.get("ticket_price", Double::class.java)

            title += if (price > 0) {
                cancelMessage = "收费活动不能取消报名"
                "【¥ %.2f】".format(price)
            } else {
                "【免费】"
            }

            var startTime = currentActivity.get("start_time", Date::class.java)
            var now = DateUtil.date().toTimestamp()
            var hours = DateUtil.between(now, startTime, DateUnit.HOUR)
            if (startTime < now || hours < 2) {
                cancelMessage = "活动开始2小时之前才能取消报名！"
            }

            map["ticket_title"] = title


            var otherInfoStr = attendUser!!.get("other_info", String::class.java)
            var otherInfo: Any? = null
            if (otherInfoStr != null && otherInfoStr != "") {
                otherInfo = mapper.readValue(otherInfoStr, Map::class.java)

                map["otherInfo"] = otherInfo
            }


        }

        var dueTime = currentActivity.get("attend_due_time", Date::class.java)
        map["cancelMessage"] = if (cancelMessage.isNullOrEmpty()) "" else cancelMessage!!
        map["is_over_due"] = dueTime <= DateUtil.date().toTimestamp()

        return map
    }

    /**
     *  免费票直接报名
     */
    @PostMapping("/attend")
    @Transactional
    fun postAttend(@RequestBody activityUser: ActivityUser): Boolean {

        activityService!!.saveAttend(activityUser)
        return true
    }


    /*
    *  免费票直接报名
    * */
    @PostMapping("/cancelattend")
    @Transactional
    fun cancelAttend(@RequestBody id: Int?): Boolean {
        activityService!!.cancelAttend(id)
        return true
    }

    /**
     * 活动报名修改页面，返回活动门票和报名相关信息
     */
    @GetMapping("/attendupdate")
    fun getAttendUpdate(request: HttpServletRequest, @RequestParam(required = false) activityId: Int?, @RequestParam(required = false) userId: Int?): Any {

        val map = mutableMapOf<String, Any?>()

        val user = userService!!.getUser(userId!!)
        val currentActivity = activityService!!.getActivityDetail(activityId)
        var community = communityService!!.getCommunity(currentActivity!!.get("community_id", Int::class.java))
        map["user"] = user
        map["userScore"] = thirdScoreService!!.getUseableScore(userId, community!!.id)
        map["scoreRate"] = env!!.getProperty("activity.score.rate", Float::class.java)


        //是否为本年的VIP
        val isVip = thirdUserService!!.isVip(community!!.id, userId!!, DateUtil.thisYear())


        var attendUserSql = "select t1.*, t2.title ticket_title, t2.memo ticket_memo, t2.price ticket_price, count ticket_count, user_level ticket_user_level from activity_user t1 inner join activity_ticket t2 on t1.activity_ticket_id = t2.id and t1.activity_id = ? and t1.user_id=?"

        var attendUser: Record? = create!!.resultQuery(attendUserSql, activityId, user.id).fetchOne()
                ?: throw Exception("您还没有报名，不能修改报名信息")

        map["attendUser"] = attendUser!!.intoMap()

        var buyTicketPrice = attendUser!!.get("ticket_price", Double::class.java)
        var buyTicketId = attendUser!!.get("activity_ticket_id", Int::class.java)

        map["activity"] = activityService!!.getActivityItem(currentActivity)

        val mapper = jacksonObjectMapper()

        var attendInfos = mapper.readValue<List<ActivityAttendInfo>>(currentActivity.get("attend_infos", String::class.java))
        attendInfos[0].value = user.realName
        attendInfos[1].value = user.username

        map["attendInfos"] = attendInfos

        var checkInScore = 0

        //积分初始化
        var scoreInfos = currentActivity.get("score_infos", String::class.java)
        if (!scoreInfos.isNullOrBlank()) {
            var scoreInfo = mapper.readValue<ActivityScoreInfo>(scoreInfos)
            checkInScore = if (isVip) {
                scoreInfo.vipUserScore
            } else {
                scoreInfo.generalUserScore
            }
        }

        map["checkInScore"] = checkInScore

        var cancelMessage: String? = null


        var ticketSql = "select t1.*, ifnull(t2.attend_count, 0) attend_count from activity_ticket t1 left join \n" +
                "( select activity_ticket_id, count(user_id) attend_count from activity_user where activity_id = ? group by activity_ticket_id ) t2\n" +
                "  on t1.id = t2.activity_ticket_id\n" +
                "  where  t1.activity_id = ?"
        var tickets = create!!.resultQuery(ticketSql, activityId, activityId).fetch()
        var ticketInfos = mutableListOf<Any>()
        var hasTickets = false
        for (ticket in tickets) {
            var map = mutableMapOf<String, Any>()
            var userLevel = if (ticket.get("user_level") == null) 0 else ticket.get("user_level", Int::class.java)
            var maxUsers = ticket.get("count", Int::class.java)
            var attendCount = ticket.get("attend_count", Int::class.java)

            var price = ticket.get("price", Double::class.java)

            var disabled = (userLevel > 0 && !isVip) || (maxUsers > 0 && attendCount >= maxUsers)

            map["checked"] = false
            if (buyTicketId == ticket.get("id", Int::class.java)) { // 自己买的票可选，默认选中
                disabled = false
                map["checked"] = true
            } else if (!disabled && price > 0) { // 修改活动票时有付费的不可选择
                disabled = true
            } else if (!disabled && buyTicketPrice > 0) { // 修改活动票时已经买过付费票的不可调整票
                disabled = true
            }

            map["disabled"] = disabled
            if (!hasTickets && !disabled) {
                hasTickets = true
            }
            var title = ""
            if (userLevel > 0) {
                title += "【马协会员专享】"
            }
            if (maxUsers > 0 && attendCount >= maxUsers) {
                title += "【满员】"
            }
            title += ticket.get("title", String::class.java)


            title += if (price > 0) {
                "【¥ %.2f】".format(price)
            } else {
                "【免费】"
            }
            map["title"] = title

            map["price"] = price

            map["id"] = ticket.get("id")
            ticketInfos.add(map)
        }
        map["hasTickets"] = hasTickets
        map["ticketInfos"] = ticketInfos


        //已报名，填充报名信息

        var title = attendUser!!.get("ticket_title", String::class.java)
        var level = attendUser!!.get("ticket_user_level", Int::class.java)
        if (level > 0) {
            title += "【马协会员专享】"
        }
        var price = attendUser!!.get("ticket_price", Double::class.java)

        title += if (price > 0) {
            cancelMessage = "收费活动不能取消报名"
            "【¥ %.2f】".format(price)
        } else {
            "【免费】"
        }

        var startTime = currentActivity.get("start_time", Date::class.java)
        var now = DateUtil.date().toTimestamp()
        var hours = DateUtil.between(now, startTime, DateUnit.HOUR)
        if (startTime < now || hours < 2) {
            cancelMessage = "活动开始2小时之前才能取消报名！"
        }

        map["ticket_title"] = title


        var otherInfoStr = attendUser!!.get("other_info", String::class.java)
        var otherInfo: Any? = null
        if (otherInfoStr != null && otherInfoStr != "") {
            otherInfo = mapper.readValue(otherInfoStr, Map::class.java)

            map["otherInfo"] = otherInfo
        }


        var dueTime = currentActivity.get("attend_due_time", Date::class.java)
        map["cancelMessage"] = if (cancelMessage.isNullOrEmpty()) "" else cancelMessage!!
        map["is_over_due"] = dueTime <= DateUtil.date().toTimestamp()


        return map
    }


    /**
     *  修改报名信息，只能修改不收费的报名记录
     */
    @PostMapping("/attendupdate")
    @Transactional
    fun postAttendUpdate(@RequestBody activityUser: ActivityUser): Boolean {

        activityService!!.updateAttend(activityUser)
        return true
    }

    @GetMapping("/checkin")
    @Transactional
    fun getCheckIn(request: HttpServletRequest, @RequestParam(required = true) activityId: Int, @RequestParam(required = true) userId: Int): Any {

        val result = mutableMapOf<String, Any?>()


        var activitySql = "select t1.*, t2.user_id , t2.check_in_time, t2.status as zqStatus, ifnull(t3.score, 0) check_in_score from activity t1 left join activity_user t2 on t1.id = t2.activity_id and t2.user_id = ? left join score_history t3 on t3.activity_id = t1.id and t3.user_id =?  where t1.id=?"


        var checkInUser = create!!.resultQuery(activitySql, userId, userId, activityId).fetchOne()

        var communityId = checkInUser.get("community_id") as Int
        //活动类型
        var activityType = checkInUser.get("activity_type") as Int
        //获取中签状态
        var zqStatus = checkInUser.get("zqStatus") as Int?

        //是否为本年的VIP
        val isVip = thirdUserService!!.isVip(communityId, userId, DateUtil.thisYear())

        var activity = activityService!!.getActivityDetail(activityId)

        var realCheckInScore = checkInUser.get("check_in_score", Int::class.java)

        var checkInScore = 0
        //积分初始化
        var activityScoreInfos = activity.get("score_infos", String::class.java)
        if (!activityScoreInfos.isNullOrBlank()) {
            val mapper = jacksonObjectMapper()
            var scoreInfo = mapper.readValue<ActivityScoreInfo>(activityScoreInfos)
            checkInScore = if (isVip) {
                scoreInfo.vipUserScore
            } else {
                scoreInfo.generalUserScore
            }
        }
        //报名截止时间
        var dueTime = checkInUser.get("attend_due_time", Date::class.java)
        //是否已过截止时间 true-已过   false-未过
        var isOverdue = dueTime <= DateUtil.date().toTimestamp()

        var checkInTime = checkInUser.get("check_in_time")
        var checkInUserId = checkInUser.get("user_id")
        //判断是否刚签到
        var isCheckInTimeNow = false

        //未过截至时间，已报名，不是中签活动 或者 中签活动中签，未签到
        if (!isOverdue && checkInUserId != null && ((activityType!=3)||(activityType==3 && zqStatus==2)) && checkInTime == null) {
            isCheckInTimeNow = true //刚签到
            checkInTime = DateUtil.date().toTimestamp()
            create!!.execute("update activity_user set check_in_time=? where activity_id=? and user_id=?", checkInTime, activityId, userId)

            if (checkInScore > 0 && realCheckInScore == 0) {
                realCheckInScore = checkInScore
                var scoreHistory = ScoreHistory()
                scoreHistory.score = realCheckInScore
                scoreHistory.activityId = activityId
                scoreHistory.userId = userId
                scoreHistory.memo = "活动签到获取积分"
                scoreHistory.created = checkInTime
                scoreHistory.communityId = communityId
                scoreService!!.save(scoreHistory)
            }
        }
        if(checkInTime != null){
            result["checkInTime"] = util!!.fromNow(checkInTime)
        }
        result["checkInScore"] = checkInScore

        //是否已过截止时间
        result["isOverdue"] = isOverdue
        //是否已报名 null-未报名
        result["checkInUserId"] = checkInUserId
        //中签状态
        result["zqStatus"] = zqStatus

        result["activity"] = activityService!!.getActivityItem(activity)

        var checkInCountSql = "select count(user_id) check_in_count from activity_user where activity_id =? and check_in_time is not null"

        var checkInCount = create!!.resultQuery(checkInCountSql, activityId).fetchOne().get("check_in_count")

        result["checkInCount"] = checkInCount

        result["is_CheckInTimeNow"] = isCheckInTimeNow


        return result
    }


   /* @CrossOrigin
    @GetMapping("/getMyActivities")
    fun getMyActivities(@RequestParam(required = false) communityId: Int?,activityId: Int?):Any{
        var activities=activityService!!
    }*/
}