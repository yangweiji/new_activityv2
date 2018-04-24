package com.kylin.activity.controller.pub

import com.kylin.activity.config.WechatMpProperties
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("pub/activity")
@SessionAttributes("username")
class ActivityDetailController : BaseController() {


    @Autowired
    private val wechatProperties: WechatMpProperties? = null


    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动发布
     */
    @GetMapping("/publish")
    fun publish(model: Model, map: ModelMap): String {

        return "pub/activity/publish"
    }

    /**
     * 活动详情
     */
    @GetMapping("/detail/{id}")
    fun detail(@PathVariable("id") id: Int, model: Model): String {

        var activity = activityService!!.getActivityDetail(id)
        if (activity["avatar"] != null)
        {
            activity.setValue(Tables.ACTIVITY.AVATAR, commonService!!.getDownloadUrl(activity.get("avatar", String::class.java)))
        }

        if (activity["user_avatar"] != null)
        {
            activity.setValue(activity.fieldsRow().field("user_avatar", String::class.java), commonService!!.getDownloadUrl(activity.get("user_avatar").toString(), "small"))
        }
        model.addAttribute("activity", activity)
        return "pub/activity/detail"
    }

    /**
     * 活动报名二维码图片
     */
    @GetMapping(value ="/attendqrcode/{id}", produces = arrayOf(MediaType.IMAGE_PNG_VALUE))
    @ResponseBody
    fun attendQrcode(@PathVariable("id") id: Int): Any {
        var url = wechatProperties!!.baseUrl + "sec/activity/attend/" + id
        val inputStream = QRCode.from(url).to(ImageType.PNG).withSize(500,500).stream()
        return inputStream.toByteArray()
    }

    /**
     * 活动签到二维码图片
     */
    @GetMapping(value ="/checkinqrcode/{id}", produces = arrayOf(MediaType.IMAGE_PNG_VALUE))
    @ResponseBody
    fun checkInQrcode(@PathVariable("id") id: Int): Any {
        var url = wechatProperties!!.baseUrl + "sec/activity/checkin/" + id
        val inputStream = QRCode.from(url).to(ImageType.PNG).withSize(500,500).stream()
        return inputStream.toByteArray()
    }




}