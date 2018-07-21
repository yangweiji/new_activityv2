package com.kylin.activity.controller.pub

import com.kylin.activity.service.ActivityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.text.ParseException


/**
 * Created by 9kylin on 2018-06-12.
 */
@RestController
@RequestMapping("pub/wx/search")
class WxSearchController  {
    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null
    /**
     * 搜索活动
     * /pub/search/s;tag=0;time=0;pay=0
     *
     * @param tag: 活动标签
     * @param time: 活动时间
     * @param searchText: 搜索输入文本
     * @return
     */
    @RequestMapping(value = "/activities", method = arrayOf(RequestMethod.GET))
    @Throws(ParseException::class)
    private fun searchActivities(
            @RequestParam(defaultValue = "0") tag: String,
            @RequestParam(defaultValue = "0") time: String,
            @RequestParam(defaultValue = "0") searchText: String?
          ): Any {
        //取得活动信息
        var activities= activityService!!.getPublicActivities(tag, time, "",searchText)
        var result = mutableListOf<Any>()
        if(activities.isNotEmpty)
        activities.forEach {  result.add(it.intoMap())}
        return result
    }
}