package com.kylin.activity.controller.pub

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.ActivityPhoto
import com.kylin.activity.service.ActivityPhotoService
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
    private var activityPhotoService: ActivityPhotoService? = null

    /**
     * 通用服务
     */
    @Autowired
    private var commonService: CommonService? = null

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
    @RequestMapping(value = "/detail/{activityPhotoId}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activityPhotoDetail(@PathVariable("activityPhotoId") activityPhotoId: Int, model: Model): String {
        var activityPhoto = activityPhotoService!!.getActivityPhotoItem(activityPhotoId)
        model.addAttribute("activityPhoto", activityPhoto)

        var activityPhotoPictureItems = activityPhotoService!!.getPhotoPictureList(activityPhotoId)
        for (item in activityPhotoPictureItems) {
            item.picture = commonService!!.getDownloadUrl(item.picture)
        }

        model.addAttribute("activityPhotoPictureItems", activityPhotoPictureItems)

        return "pub/activityphoto/detail"
    }
}