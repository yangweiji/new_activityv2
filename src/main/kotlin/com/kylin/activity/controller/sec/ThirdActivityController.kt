package com.kylin.activity.controller.sec

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoPictureDao
import com.kylin.activity.databases.tables.daos.ActivityTicketDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.databases.tables.pojos.ActivityPhotoPicture
import com.kylin.activity.databases.tables.pojos.ActivityTicket
import com.kylin.activity.model.ActivityAttendInfo
import com.kylin.activity.model.ActivityScoreInfo
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ThirdActivityService
import com.kylin.activity.service.UserService
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

data class ThirdActivityPublishData(
        var activity: Activity? = null,
        var tickets: List<ActivityTicket>? = null,
        var attendInfos: List<ActivityAttendInfo>? = null,
        var canAttend: Boolean = false,
        var activityType: Int? = 0,
        var scoreInfos: ActivityScoreInfo? = null
)

data class PhotosData(
        var activityPhoto: ActivityPhoto? = null
)

@Controller
@RequestMapping("sec/thirdactivity")
class ThirdActivityController : BaseController() {
    @Autowired
    private val activityService: ThirdActivityService? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null

    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val activityPhotoPictureDao: ActivityPhotoPictureDao? = null



    /**
     * 第三方活动管理之
     * 查询活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/activities", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun activities(): String {
        return "sec/thirdactivity/activities"
    }

    /**
     *
     * 第三方活动管理之
     * 异步查询活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getActivities", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun activities(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var status = map["status"]
        var tags = map["tags"]
        var title = map["title"]
        var items = activityService!!.getAllActivityUserItemsAndCommunity(title, tags, status, this.sessionCommunity.id)
        var list = items.intoMaps()
        return list
    }


    /**
     * 第三方最新活动
     * 之编辑（id:Int）和添加(type:Int)活动信息
     */
    @GetMapping("/publish")
    fun getPublish(@RequestParam(required = false) id: Int?, @RequestParam(required = false) type: Int?, model: Model): String {
        //检查用户权限
        if (!userService!!.checkPermission("PUBLISH")) {
            return "pub/error/20"
        }

        var data = ThirdActivityPublishData()
        if (id != null && id > 0) {
            data.activity = activityDao!!.fetchOneById(id)
            data.tickets = activityTicketDao!!.fetchByActivityId(id)
            val mapper = jacksonObjectMapper()
            data.attendInfos = mapper.readValue<List<ActivityAttendInfo>>(data.activity!!.attendInfos)
            data.canAttend = data.activity!!.endTime == data.activity!!.attendDueTime
            data.activityType = data.activity!!.activityType
            if (data.activity!!.scoreInfos != null && data.activity!!.scoreInfos != "") {
                data.scoreInfos = mapper.readValue<ActivityScoreInfo>(data.activity!!.scoreInfos)
            } else {
                data.scoreInfos = ActivityScoreInfo()
            }
        } else {
            data.activityType = type!!
            data.activity = Activity()
            data.activity!!.activityType = data.activityType
            data.tickets = listOf(ActivityTicket())
            data.attendInfos = listOf(
                    ActivityAttendInfo(title = "姓名", type = "text", required = true),
                    ActivityAttendInfo(title = "手机", type = "text", required = true)
            )
            data.canAttend = true
        }
        model.addAttribute("typeName", if (data.activityType == 2) "赛事" else "活动")
        model.addAttribute("data", data)
        return "sec/thirdactivity/publish"
    }

    /**
     * 第三方最新活动
     * 之编辑（id:Int）和添加(type:Int)活动信息发布成功
     */
    @RequestMapping("/publish")
    @Transactional
    fun postPublish(@RequestBody formData: MultiValueMap<String, String>, model: Model): String {
        var jsonData = formData["json_data"]!![0]

        val mapper = jacksonObjectMapper()
        var data = mapper.readValue(jsonData, ThirdActivityPublishData::class.java)

        //设置活动为公开状态
        data.activity!!.public = true

        if (data.canAttend) {
            data.activity!!.attendDueTime = data.activity!!.endTime
        }
        data.activity!!.attendInfos = mapper.writeValueAsString(data.attendInfos)
        data.activity!!.scoreInfos = mapper.writeValueAsString(data.scoreInfos)

        var user = userService!!.getCurrentUserInfo()
        data.activity!!.created = DateUtil.date().toTimestamp()
        data.activity!!.createdBy = user!!.id

        if (data.activity!!.id == null || data.activity!!.id == 0) {
            activityDao!!.insert(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            activityTicketDao!!.insert(data.tickets!!.toList())
        } else {
            activityDao!!.update(data.activity)
            data.tickets!!.forEach { t -> t.activityId = data.activity!!.id }
            create!!.deleteFrom(Tables.ACTIVITY_TICKET)
                    .where(Tables.ACTIVITY_TICKET.ACTIVITY_ID.eq(data.activity!!.id))
                    .execute()
            activityTicketDao!!.insert(data.tickets!!.toList())
        }
        return "redirect:/sec/thirdactivity/result?success&type=${data.activity!!.activityType}&id=${data.activity!!.id}"
    }

    /**
     * 跳转到编辑活动发布成功页面
     */
    @GetMapping("/result")
    fun result(): String {
        return "sec/thirdactivity/result"
    }

    /**
     * 第三方最新活动签到二维码
     * @param model
     * *
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/qrcode", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun qrCode(@RequestParam id: Int, model: Model): String {
        val activity = activityService!!.getActivityAndOthers(id)
        model.addAttribute("activity", activity)
        return "sec/thirdactivity/qrcode"
    }

    /**
     * 第三方最新活动
     * 报名签到信息
     */
    @CrossOrigin
    @RequestMapping(value = "/attendusers", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun attendusers(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        //参数：手机号
        var activityId = request.getParameter("activityId")
        var title = request.getParameter("title")
        var mobile = request.getParameter("mobile")
        var real_name = request.getParameter("real_name")
        var ticket_title = request.getParameter("ticket_title")
        var checked = request.getParameter("checked")
        var status = request.getParameter("status")
        var other_info = request.getParameter("other_info")

        //取得活动报名信息
        val items = activityService!!.getAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }

        //取得统计统计信息
        val activityStatistics = activityService!!.getActivityStatisticsByTicket(activityId)
        //取得活动报名人数
        var attendcount = activityService!!.getActivityAttendCount(activityId)
        //取得活动签到人数
        var checkcount = activityService!!.getActivityCheckCount(activityId)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        //将活动报名信息添加至数据模型
        model.addAttribute("attendColumns", attendColumns)
        model.addAttribute("items", listItems)

        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendcount", attendcount)
        model.addAttribute("checkcount", checkcount)
        return "sec/thirdactivity/attendusers"
    }

    /**
     * 第三方最新活动报名签到信息
     * 之取得用户信息集合
     */
    @CrossOrigin
    @RequestMapping(value = "/getAttendUsers", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun attendusers(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        val start = map["start"]
        var end = map["end"]
        var activityId = map["activityId"]
        var title = map["title"]
        var mobile = map["mobile"]
        var real_name = map["real_name"]
        var ticket_title = map["ticket_title"]
        var checked = map["checked"]
        var status = map["status"]
        var other_info = map["other_info"]

        //取得活动报名信息
        val items = activityService!!.getAttendUsers(start, end, activityId, title, mobile, real_name, ticket_title, checked, status, other_info)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for (item in items) {
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for (info in otherInfo) {
                var key = "报名：" + info.key
                if (!attendColumns.contains(key)) {
                    attendColumns.add(key)
                }
                map.put(key, info.value)
            }

            listItems.add(map)
        }
        return listItems
    }

    /**
     * 第三方活动管理之
     * 删除活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteActivity/{id}", method = arrayOf(RequestMethod.POST))
    fun deleteActivity(@PathVariable id: Int, model: Model): String {
        activityService!!.deleteById(id)
        return "redirect:/sec/thirdactivity/activities"
    }

    /**
     * 第三方活动
     * 之报名信息
     */
    @CrossOrigin
    @RequestMapping(value = "/attend/{activityId}")
    fun attend(@PathVariable activityId: Int, model: Model): String {
        var user = this.sessionUser
        model.addAttribute("user", user)
        var activity = activityService!!.getActivityAndOthers(activityId)
        model.addAttribute("activity", activity)
        return "sec/thirdactivity/attend"
    }

}