package com.kylin.activity.controller.sec

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityTicketDao
import com.kylin.activity.databases.tables.pojos.Activity
import com.kylin.activity.databases.tables.pojos.ActivityFavorite
import com.kylin.activity.databases.tables.pojos.ActivityTicket
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.model.ActivityAttendInfo
import com.kylin.activity.model.ActivityScoreInfo
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.UserService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

data class ActivityPublishData (
    var activity: Activity? = null,
    var tickets: List<ActivityTicket>? = null,
    var attendInfos: List<ActivityAttendInfo>? = null,
    var canAttend: Boolean = false,
    var activityType:Int? = 0,
    var scoreInfos: ActivityScoreInfo? = null
)

@Controller
@RequestMapping("sec/activity")
class ActivityController : BaseController() {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val activityTicketDao: ActivityTicketDao? = null

    @Autowired
    private val create: DSLContext? = null

    @GetMapping("/publish")
    fun getPublish(@RequestParam(required = false) id:Int?, @RequestParam(required = false) type:Int?, model: Model): String {

        //检查用户权限
        if(!userService!!.checkPermission("PUBLISH")){
            return "pub/error/20"
        }

        var data = ActivityPublishData()
        if(id != null && id > 0) {
            data.activity = activityDao!!.fetchOneById(id)
            data.tickets = activityTicketDao!!.fetchByActivityId(id)
            val mapper = jacksonObjectMapper()
            data.attendInfos = mapper.readValue<List<ActivityAttendInfo>>(data.activity!!.attendInfos)
            data.canAttend = data.activity!!.endTime == data.activity!!.attendDueTime
            data.activityType= data.activity!!.activityType
            if(data.activity!!.scoreInfos != null && data.activity!!.scoreInfos != "") {
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
                    ActivityAttendInfo( title = "姓名", type = "text", required = true),
                    ActivityAttendInfo( title = "手机", type = "text", required = true)
            )
            data.canAttend = true
        }
        model.addAttribute("typeName", if (data.activityType == 2) "赛事" else "活动" )
        model.addAttribute("data", data)
        return "sec/activity/publish"
    }

    @PostMapping("/publish")
    @Transactional
    fun postPublish(@RequestBody formData:MultiValueMap<String, String>, model: Model): String {
        var jsonData = formData["json_data"]!![0]

        val mapper = jacksonObjectMapper()
        var data = mapper.readValue(jsonData, ActivityPublishData::class.java)

        //设置活动为公开状态
        data.activity!!.public = true

        if(data.canAttend){
            data.activity!!.attendDueTime = data.activity!!.endTime
        }
        data.activity!!.attendInfos = mapper.writeValueAsString(data.attendInfos)
        data.activity!!.scoreInfos = mapper.writeValueAsString(data.scoreInfos)

        var user = userService!!.getCurrentUserInfo()
        data.activity!!.created = DateUtil.date().toTimestamp()
        data.activity!!.createdBy = user!!.id

        if(data.activity!!.id == null || data.activity!!.id == 0) {
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

        return "redirect:/sec/activity/result?success&type=${data.activity!!.activityType}&id=${data.activity!!.id}"
    }

    @GetMapping("/result")
    fun result(model: Model): String {
        return "sec/activity/result"
    }

    /**
     * 报名
     */
    @GetMapping("/attend/{activityId}")
    fun attend(@PathVariable activityId: Int,
               request: HttpServletRequest,
               model: Model): String {
        //从session中当前用户信息
        var user = this.sessionUser
        model.addAttribute("user", user)

//        if (user!!.isReal == null || !user!!.isReal) {
//            //设置当前请求路径至Request中，转发处理后重新定向回来
//            request.setAttribute("current_url", request.requestURI)
//            //如果用户不是会员，则需要注册成为会员
//            return "forward:/sec/user/registermember"
//        }

        //取得活动详情信息
        var activity = activityService!!.getActivityAndOthers(activityId)
        model.addAttribute("activity", activity)
        return "sec/activity/attend"
    }

    /**
     * 活动
     * @param model
     * *
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/activities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activities(request: HttpServletRequest, model: Model): String {
        val title = request.getParameter("title")
        val activityItems = activityService!!.getAllActivityUserItems(title)
        model.addAttribute("activities", activityItems)
        return "sec/activity/activities"
    }

    /**
     * 报名信息
     * @param request
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/attendusers", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun attendusers(request: HttpServletRequest, model: Model): String {
        //参数：手机号
        var activityId = request.getParameter("activityId")
        var title = request.getParameter("title")
        var mobile = request.getParameter("mobile")
        var real_name = request.getParameter("real_name")
        var ticket_title = request.getParameter("ticket_title")
        var checked = request.getParameter("checked")

        //取得活动报名信息
        val items = activityService!!.getAttendUsers(activityId, title, mobile, real_name, ticket_title, checked)

        var listItems = mutableListOf<Any>()
        var attendColumns = mutableListOf<String>()
        val mapper = jacksonObjectMapper()
        for( item in items){
            var map = item.intoMap()
            var otherInfo = mapper.readValue<Map<String, String>>(map.get("other_info").toString())
            for(info in otherInfo){
                var key = "报名：" + info.key
                if(!attendColumns.contains(key)){
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




        //将活动报名信息添加至数据模型
        model.addAttribute("attendColumns", attendColumns)
        model.addAttribute("items", listItems)
        //将活动统计信息添加至数据模型
        model.addAttribute("activityStatistics", activityStatistics)
        model.addAttribute("attendcount", attendcount)
        model.addAttribute("checkcount", checkcount)
        return "sec/activity/attendusers"
    }

    /**
     * 取得活动信息集合
     */
    private val getActivities: List<Activity>
        @RequestMapping(value = "/getActivities")
        @ResponseBody
        get() {
            val items = activityService!!.getAllActivities()
            return items
        }

    /**
     * 删除活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteActivity/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteActivity(@PathVariable id: Int, model: Model): String {
        activityService!!.deleteById(id)

        return "redirect:/sec/activity/activities"
    }

    /**
     * 收藏活动，返回活动收藏数
     */
    @RequestMapping(value = "/favorite", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun favorite(@RequestParam id: Int): Int {
        var activityFavorite = ActivityFavorite()
        activityFavorite.activityId = id
        activityFavorite.userId = this.sessionUser.id
        //创建活动收藏
        activityService!!.createActivityFavorite(activityFavorite)

        return activityService!!.getActivityFavoriteCount(id)
    }

    /**
     * 活动签到二维码
     * @param model
     * *
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/qrcode", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun qrCode(@RequestParam id: Int, model: Model): String {
        val activity = activityService!!.getActivityAndOthers(id)

        model.addAttribute("activity", activity)
        return "sec/activity/qrcode"
    }

}