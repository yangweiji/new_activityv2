package com.kylin.activity.controller.pub

import com.kylin.activity.config.ActivityProperties
import com.kylin.activity.controller.BaseController
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.text.ParseException

/**
 * Created by 9kylin on 2017-12-06.
 */
@Controller
@RequestMapping("pub")
class SearchController : BaseController() {
    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动配置
     */
    @Autowired
    private val activityProperties: ActivityProperties? = null

    /**
     * 图片真实URL
     * @param fileId
     * @return
     */
    @RequestMapping("/images")
    @ResponseBody
    fun getImageUrl(@RequestParam("fileId") fileId: String): String {
        return commonService!!.getDownloadUrl(fileId, "middle")
    }

    /**
     * 搜索活动
     * /pub/search/s;tag=0;time=0;pay=0
     *
     * @param tag: 活动标签
     * @param time: 活动时间
     * @param pay: 活动费用
     * @param page: 分页索引
     * @param model：模型数据
     * @return
     */
    @RequestMapping(value = "/search/{s}", method = [RequestMethod.GET])
    @Throws(ParseException::class)
    private fun search(
            @PathVariable s: Int?,
            @MatrixVariable(defaultValue = "0") tag: String,
            @MatrixVariable(defaultValue = "0") time: String,
            @MatrixVariable(defaultValue = "0") pay: String,
            @MatrixVariable(defaultValue = "0") page: Int?,
            model: Model): String {
        var s = s

        //设置记录数
        activityService!!.size = activityProperties!!.pageSize
        //设置起始记录位置
        activityService!!.page = page!!

        //取得活动信息
        var activities = activityService!!.getPublicActivities(tag, time, pay)
        val pageable = PageRequest(page!!, activityService!!.size)
        val pageImpl = PageImpl(activities, pageable, activityService!!.activityCount)
        model.addAttribute("pageImpl", pageImpl)
        model.addAttribute("first", pageImpl.isFirst)
        model.addAttribute("last", pageImpl.isLast)

        if (s == null) {
            s = 1
        }

        if (s == 2) {
            //人气最高
            activities = activities.sortDesc("attend_user_count")
        } else {
            //最新活动
            activities = activities.sortDesc("start_time")
        }

        if (time.length > 1) {
            //手工选择日期
            val format = DateTimeFormat.forPattern("yyyyMMdd")
            val dateTime = DateTime.parse(time, format)
            model.addAttribute("start_date", dateTime.toString("yyyy-MM-dd"))
        }
        model.addAttribute("s", s)
        model.addAttribute("tag", tag)
        model.addAttribute("time", time)
        model.addAttribute("pay", pay)
        model.addAttribute("page", page)
        model.addAttribute("activities", activities)

        return "pub/search"
    }


}
