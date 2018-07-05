package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
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
import javax.servlet.http.HttpServletRequest

/**
 * 图片数据类
 */
data class PicturesData(
        var activityPhotoPicture: ActivityPhotoPicture? = null
)

@Controller
@RequestMapping("sec/community/thirdactivity")
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
     * 相册管理，根据活动相册查询图片信息
     * @param user 用户信息
     * @param activityId 活动相册外键:活动id
     * @param model 模型数据
     * @return 图片信息集合
     */
    @RequestMapping(value = "/activityphotos", method = [RequestMethod.GET, RequestMethod.POST])
    fun activityPhotos(@RequestParam(required = false) activityId: Int?, model: Model): String {
        var user = this.sessionUser
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
                    , axtenalUrl
                    , null)
            //添加到数据库
            activityPhotoService!!.insert(activityPhoto)
            if (activityPhoto.id > 0) {
                LogUtil.printLog("创建相册信息OK, 相册ID: ${activityPhoto.id}")
            }
        } else {
            activityPhoto.picture = activity!!.avatar
            activityPhoto.description = activity!!.title
            activityPhoto.created = DateUtil.date().toTimestamp()
            activityPhoto.createdBy = user!!.id
            activityPhoto.axtenalUrl = commonService!!.getDownloadUrl(activityPhoto.picture)
            //更新相册信息
            activityPhotoService!!.update(activityPhoto)
            LogUtil.printLog("更新相册信息OK, 相册ID: ${activityPhoto.id}")
        }

        //添加活动相册到模型数据中
        model.addAttribute("activityPhoto", activityPhoto)
        //获取相册对应的所有图片集合信息,sortedByDescending { it.id }场景如：添加时根据id添加的数据摆放在第一的位置，而不是末端位置
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

        return "sec/community/thirdactivity/activityphotos"
    }

    /**
     * 保存图片信息
     * @param activityPhotoPicture 图片信息
     * @param activityId 活动id(活动相册的外键)
     */
    @PostMapping("/savePictures")
    fun savePictures(@ModelAttribute("activityPhotoPicture") activityPhotoPicture: ActivityPhotoPicture
                     , @RequestParam(required = false) activityId: Int?
                     , redirectAttributes: RedirectAttributes, model: Model): String {
        var user = this.sessionUser
        activityPhotoPicture.created = DateUtil.date().toTimestamp()
        activityPhotoPicture.createdBy = user!!.id
        activityPhotoPicture.order = null
        if (activityPhotoPicture.picture == "") {
            redirectAttributes.addFlashAttribute("errorMessage", "请选择上传图片！")
            return "redirect:/sec/community/thirdactivity/activityphotos?activityId=${activityId}"
        }
        activityPhotoService!!.insertPicture(activityPhotoPicture)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        LogUtil.printLog("添加图片OK, 图片ID: ${activityPhotoPicture.id}")

        return "redirect:/sec/community/thirdactivity/activityphotos?activityId=${activityId}"
    }

    /**
     * 删除相册
     * @param request: 参数
     */
    @RequestMapping(value = "/delete", method = [RequestMethod.POST])
    @ResponseBody
    fun delete(request: HttpServletRequest): Any? {

        var activityPhotoId = if (request.getParameter("activityPhotoId") != null) request.getParameter("activityPhotoId").toInt() else 0
        activityPhotoService!!.delete(activityPhotoId)

        return true
    }


    /**
     * 删除图片
     * @param request: 参数
     */
    @RequestMapping(value = "/deletePicture", method = [RequestMethod.POST])
    @ResponseBody
    fun deletePicture(request: HttpServletRequest): Any? {

        var pictureId = if (request.getParameter("pictureId") != null) request.getParameter("pictureId").toInt() else 0
        activityPhotoService!!.deletePicture(pictureId)

        return true
    }
}
