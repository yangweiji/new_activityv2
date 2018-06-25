package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.pojos.ActivityUserRecord
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ActivityUserRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 微信打卡活动相关控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/activityuserrecord")
class WxActivityUserRecordController {

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null


    /**
     * 打卡活动服务
     */
    @Autowired
    private val activityUserRecordService: ActivityUserRecordService? = null

    /**
     *  获取打卡活动的打卡记录
     */
    @GetMapping("/records")
    fun getRecords(@RequestParam(required = true) activityId: Int): Any {
        var records = activityUserRecordService!!.getRecordsByActivityId(activityId)

        var item = activityService!!.getActivityDetail(activityId)
        var activity = activityService!!.getActivityItem(item)

        activity["start"] = item["start_time"]
        activity["end"] = item["end_time"]

        var resuls = mutableListOf<Any>()
        if(records != null){
            records.forEach { resuls.add(it.intoMap()) }
        }
        return mapOf( "records" to resuls, "activity" to activity)
    }


    /**
     * 获取打卡记录
     */
    @GetMapping("/get")
    fun getRecord(@RequestParam(required = true) id:Int): Any{
        return activityUserRecordService!!.getRecord(id)
    }

    /**
     * 保存打卡记录
     */
    @PostMapping("/save")
    fun saveRecord(@RequestBody record:ActivityUserRecord){
        activityUserRecordService!!.saveRecord(record)
    }

    /**
     * 取消打卡记录
     */
    @PostMapping("/remove")
    fun removeRecord(@RequestBody recordId: Int):Int{
        return activityUserRecordService!!.removeRecord(recordId)
    }
}