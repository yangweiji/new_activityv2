package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.ThirdPhotoService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.servlet.mvc.support.RedirectAttributes

data class ActivityPhotoData(
        var activityphoto: ActivityPhoto? = null
)

/**
 * 安全控制访问
 * 第三方团体组织相册管理
 */
@Controller
@RequestMapping("sec/community/thirdphotos")
class ThirdPhotoController : BaseController() {

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 相册服务
     */
    @Autowired
    private val thirdPhotoService: ThirdPhotoService? = null


    /**
     * 活动相册服务
     */
    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

    /**
     * 查询第三方活动相册
     */
    @CrossOrigin
    @RequestMapping(value = "/photos", method = [RequestMethod.GET, RequestMethod.POST])
    fun photos(): String {
        return "sec/community/thirdphotos/photos"
    }

    /**
     * 异步获取第三方活动相册
     * @param map: 条件参数Map
     * @return 活动相册结果集合
     */
    @CrossOrigin
    @RequestMapping(value = "/getActivityPhotos", method = [RequestMethod.GET, RequestMethod.POST])
    @ResponseBody
    fun getActivityPhotos(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var description = map["description"]
        var items = thirdPhotoService!!.getAllPhotosItems(description, this.sessionCommunity.id)
        var lists = items.intoMaps()
        return lists
    }

    /**
     * 删除活动相册
     * @param activityPhotoId: 相册ID
     * @return 相册管理视图页面
     */
    @RequestMapping(value = "/deleteActivityPhoto/{activityPhotoId}")
    fun deleteActivityPhoto(@PathVariable("activityPhotoId") activityPhotoId: Int
        , redirectAttributes: RedirectAttributes): String {
        //删除相册
        activityPhotoService!!.delete(activityPhotoId)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")

        return "redirect:/sec/community/thirdphotos/photos"
    }

    /**
     * 创建活动相册
     * @param activityPhotoId: 相册ID
     * @param activityId: 活动ID
     * @return 创建相册视图页面
     */
    @GetMapping("/createActivityPhoto")
    fun createActivityPhoto(@RequestParam(required = false) activityPhotoId: Int?
                            ,@RequestParam(required = false) activityId: Int?
                            , model: Model): String {
        //创建一个新相册
        var activityPhoto = ActivityPhoto()
        if (activityPhotoId != null && activityPhotoId > 0) {
            activityPhoto = thirdPhotoService!!.getActivityPhoto(activityPhotoId)
        }

        model.addAttribute("activityPhoto", activityPhoto)

        return "sec/community/thirdphotos/createActivityPhoto"
    }

    /**
     * 保存相册
     * @param activityPhoto: 活动相册
     * @param redirectAttributes: 跳转属性
     * @param model: 模型
     * @return 视图页面
     */
    @RequestMapping(value = "/saveActivityPhoto", method = [RequestMethod.POST])
    @Transactional
    fun saveActivityPhoto(@ModelAttribute("activityPhoto") activityPhoto: ActivityPhoto
                          , redirectAttributes: RedirectAttributes, model: Model): String {
        var user=this.sessionUser
        //验证活动编号是否有效
        var activity = activityService!!.getCommunityActivity(activityPhoto.activityId, this.sessionCommunity.id)
        if (activity == null)
        {
            model.addAttribute("errorMessage", "活动编号: ${activityPhoto.activityId} 无效！")
            return "sec/community/thirdphotos/createActivityPhoto"
        }

        var activityPhotoItems = activityPhotoService!!.getActivityPhotos(activityPhoto.activityId)
        if (activityPhotoItems.isNotEmpty())
        {
            model.addAttribute("errorMessage", "活动编号: ${activityPhoto.activityId} 对应的相册已存在！")
            return "sec/community/thirdphotos/createActivityPhoto"
        }

        activityPhoto.axtenalUrl = commonService!!.getDownloadUrl(activityPhoto.picture)
        if (activityPhoto.id == null || activityPhoto.id == 0) {
            activityPhoto.created = DateUtil.date().toTimestamp()
            activityPhoto.createdBy = user!!.id
            activityPhotoService!!.insert(activityPhoto)
            LogUtil.printLog("创建相册OK, 相册ID: ${activityPhoto.id}")
        } else {
            activityPhotoService!!.update(activityPhoto)
            LogUtil.printLog("更新相册OK, 相册ID: ${activityPhoto.id}")
        }

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")

        return "redirect:/sec/community/thirdphotos/photos"
    }
}
