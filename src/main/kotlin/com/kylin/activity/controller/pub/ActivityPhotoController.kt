package com.kylin.activity.controller.pub

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.ActivityPhoto
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * 公共访问
 * 活动相册控制器
 * @author Richard C. Hu
 */
@Controller
@RequestMapping("pub/activityphoto")
@SessionAttributes("activityphoto")
class ActivityPhotoController : BaseController() {

    /**
     * 活动相册服务
     */
    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

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
     * 首页活动相册
     */
    @RequestMapping(value = "/photos", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activityPhotos(model: Model): String {
        //依据团体组织ID取得该团体下的全部活动相册信息
        var activityPhotoItems = activityPhotoService!!.getActivityPhotoItemsByCommunity(this.sessionCommunity.id)

        model.addAttribute("activityPhotoItems", activityPhotoItems)

        return "pub/activityphoto/photos"
    }

    /**
     * 活动相册详情
     * @param activityPhotoId: 相册ID
     * @return 相册详情页面视图
     */
    @CrossOrigin
    @RequestMapping(value = "/detail/{activityPhotoId}", method = [RequestMethod.GET, RequestMethod.POST])
    fun activityPhotoDetail(@PathVariable("activityPhotoId") activityPhotoId: Int, model: Model): String {

        var activityPhoto = activityPhotoService!!.getActivityPhotoItem(activityPhotoId)
        //记录浏览次数
        if (activityPhoto.browseCount != null) {
            activityPhoto.browseCount++
        } else {
            activityPhoto.browseCount = 0
        }

        activityPhotoService!!.update(activityPhoto)
        model.addAttribute("activityPhoto", activityPhoto)

        var activityPhotoPictureItems = activityPhotoService!!.getPhotoPictureList(activityPhotoId)
        for (item in activityPhotoPictureItems) {
            item.picture = commonService!!.getDownloadUrl(item.picture)
        }

        model.addAttribute("activityPhotoPictureItems", activityPhotoPictureItems)

        //添加活动至模型
        var activity = activityService!!.getActivity(activityPhoto.activityId)
        model.addAttribute("activity", activity)

        return "pub/activityphoto/detail"
    }
}