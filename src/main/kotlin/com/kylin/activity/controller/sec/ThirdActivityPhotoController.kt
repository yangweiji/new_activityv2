package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoPictureDao
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.databases.tables.pojos.ActivityPhotoPicture
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * 图片数据类
 */
data class PicturesData(
        var activityPhotoPicture: ActivityPhotoPicture? = null
)

@Controller
@RequestMapping("sec/thirdactivity")
@SessionAttributes("user")
class ThirdActivityPhotoController : BaseController() {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /***
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null


    /**
     * 活动相册服务
     */
    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

    /**
     * 活动数据访问
     */
    @Autowired
    private val activityDao: ActivityDao? = null

    /**
     * 活动相册图片数据访问
     */
    @Autowired
    private val activityPhotoPictureDao: ActivityPhotoPictureDao? = null

    /**
     * 活动相册数据访问
     */
    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null

    /**
     * 相册管理，根据活动相册查询图片信息
     * @param user 用户信息
     * @param activityId 活动相册外键:活动id
     * @param model 模型数据
     * @return 图片信息集合
     */
    @RequestMapping(value = "/activityphotos", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun getActivityPhotos(@ModelAttribute user: User, @RequestParam(required = false) activityId: Int?, model: Model): String {

        //取得活动详情信息
        var activity = activityService!!.getActivity(activityId!!)
        //存储活动详情信息至模型数据中
        model.addAttribute("activity", activity)
        //取得活动对应的唯一相册
        var activityPhoto = activityPhotoService!!.getFirstActivityPhoto(activityId)
        //检查活动相册有无相册信息，无，则添加相册信息
        if (activityPhoto == null) {
            var picture = activity!!.avatar
            var axtenalUrl = commonService!!.getDownloadUrl(picture)
            //创建活动对应的相册
            activityPhoto = ActivityPhoto(null
                    , activityId
                    , picture
                    , activity!!.title
                    , DateUtil.date().toTimestamp()
                    , user!!.id
                    , axtenalUrl)
            //添加到数据库
            var rs = activityPhotoService!!.insertActivityPhoto(activityPhoto)
            LogUtil.printLog("rs: $rs")
            if (rs > 0) {
                //获取活动对应的相册
                activityPhoto = activityPhotoService!!.getFirstActivityPhoto(activityId)
                LogUtil.printLog("创建相册信息OK, 相册ID: ${activityPhoto!!.id}")
            }
        } else {
            activityPhoto.picture = activity!!.avatar
            activityPhoto.description = activity!!.title
            activityPhoto.created = DateUtil.date().toTimestamp()
            activityPhoto.createdBy = user!!.id
            activityPhoto.axtenalUrl = commonService!!.getDownloadUrl(activityPhoto.picture)
            //更新相册信息
            activityPhotoDao!!.update(activityPhoto)
            LogUtil.printLog("更新相册信息OK, 相册ID: ${activityPhoto.id}")
        }

        //添加活动相册到模型数据中
        model.addAttribute("activityPhoto", activityPhoto)
        //获取相册对应的所有图片集合信息
        var photoPictureItems = activityPhotoService!!.getPhotoPictureList(activityPhoto.id).sortedByDescending { it.id }

        for (r in photoPictureItems) {
            //更新活动图片的URL
            if (r.picture != null) {
                r.picture = commonService!!.getDownloadUrl(r.picture)
            }
        }

        //添加相册图片到模型数据中
        model.addAttribute("photoPictureItems", photoPictureItems)
        //添加活动ID至数据模型中
        model.addAttribute("activityId", activityId)

        var picturesData = PicturesData()
        //构建图片对象
        var activityPhotoPicture = ActivityPhotoPicture()
        //设定图片归属相册ID
        activityPhotoPicture.activityPhotoId = activityPhoto.id

        picturesData.activityPhotoPicture = activityPhotoPicture

        model.addAttribute("picturesData", picturesData)
        model.addAttribute("activityPhotoPicture", activityPhotoPicture)

        return "sec/thirdactivity/activityphotos"
    }

    /**
     * 保存图片信息
     * @param activityPhotoPicture 图片信息
     */
    @PostMapping("/savePictures")
    fun savePictures(@ModelAttribute user: User
                     , @ModelAttribute("activityPhotoPicture") activityPhotoPicture: ActivityPhotoPicture
                     , @RequestParam(required = false) activityId: Int?
                     , redirectAttributes: RedirectAttributes): String {
        activityPhotoPicture.created = DateUtil.date().toTimestamp()
        activityPhotoPicture.createdBy = user!!.id
        activityPhotoPicture.order = null

        var rs = activityPhotoPictureDao!!.insert(activityPhotoPicture)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        LogUtil.printLog("添加图片OK")

        return "redirect:/sec/thirdactivity/activityphotos?activityId=${activityId}"
    }


    /**
     * 删除图片
     * @param pictureId 图片id
     * @param activityId 活动相册外键：活动id
     */
    @RequestMapping(value = "/deletePictures", method = arrayOf(RequestMethod.POST))
    fun deletePictures(model: Model,
                       @RequestParam(required = false) pictureId: Int,
                       @RequestParam(required = false) activityId: Int?): Any? {
        activityPhotoPictureDao!!.deleteById(pictureId)
        return "redirect:/sec/thirdactivity/activityphotos?activityId=" + activityId
    }
}
