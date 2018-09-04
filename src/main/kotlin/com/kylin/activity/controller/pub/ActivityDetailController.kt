package com.kylin.activity.controller.pub

import com.kylin.activity.config.WxMaProperties
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.WxService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.stream.FileImageOutputStream


@Controller
@RequestMapping("pub/activity")
@SessionAttributes("username")
class ActivityDetailController : BaseController() {


    @Autowired
    private val wechatProperties: WxMaProperties? = null


    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val commonService: CommonService? = null

    /**
     * 微信服务
     */
    @Autowired
    private val wxService: WxService? = null

    /**
     * 活动相册
     */
    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

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

        //取得活动对应相册ID
        var photo = activityPhotoService!!.getFirstActivityPhoto(id)
        model.addAttribute("photo", photo)

        return "pub/activity/detail"
    }

    /**
     * 活动报名二维码图片
     */
    @GetMapping(value ="/attendqrcode/{id}", produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun attendQrcode(@PathVariable("id") id: Int): Any {
        var page = wechatProperties!!.attendPage
        var file = wxService!!.getQrCode(id.toString(), page!!)
        LogUtil.printLog("生成小程序报名二维码OK, name:${file.name}")

        return this.readInputStream(file.inputStream())
    }

    /**
     * 活动签到二维码图片
     */
    @GetMapping(value ="/checkinqrcode/{id}", produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun checkInQrcode(@PathVariable("id") id: Int): Any {
        var page = wechatProperties!!.checkInPage
        var file = wxService!!.getQrCode(id.toString(), page!!)
        LogUtil.printLog("生成小程序签到二维码OK, name:${file.name}")

        return this.readInputStream(file.inputStream())
    }

    /**
     * 活动预览二维码图片
     */
    @GetMapping(value ="/previewqrcode/{id}", produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun previewQrcode(@PathVariable("id") id: Int): Any {
        var page = wechatProperties!!.previewPage
        var file = wxService!!.getQrCode(id.toString(), page!!)
        LogUtil.printLog("生成小程序活动预览二维码OK, name:${file.name}")

        return this.readInputStream(file.inputStream())
    }

    @Throws(Exception::class)
    private fun readInputStream(inStream: InputStream): ByteArray {
        val outStream = ByteArrayOutputStream()
        //创建一个Buffer字符串
        val buffer = ByteArray(1024)
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        var len = 0
        //使用一个输入流从buffer里把数据读取出来
        //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
        while (inStream.read(buffer).apply { len = this } != -1) {
            outStream.write(buffer, 0, len)
        }
        inStream.close()
        //把outStream里的数据写入内存
        return outStream.toByteArray()
    }
}