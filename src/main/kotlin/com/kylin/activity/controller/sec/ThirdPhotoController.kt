package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.service.ThirdPhotoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

data class ActivityPhotoData(
        var activityphoto: ActivityPhoto? = null
)


@Controller
@RequestMapping("sec/thirdphotos")
@SessionAttributes("activityphotos")
class ThirdPhotoController : BaseController() {

    @Autowired
    private val thirdPhotoService: ThirdPhotoService? = null

    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null

    /**
     * 查询第三方活动相册
     */
    @CrossOrigin
    @RequestMapping(value = "/photos", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun queryPhoto(): String {
        return "sec/thirdphotos/photos"
    }

    /**
     * 异步获取第三方活动相册
     */
    @CrossOrigin
    @RequestMapping(value = "/getActivityPhotos", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun getActivityPhotos(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var description = map["description"]
        var items = thirdPhotoService!!.getAllPhotosItems(description,this.sessionCommunity.id)
        var lists = items.intoMaps()
        return lists
    }

    /**
     * 删除活动相册
     */
    @RequestMapping(value = "/deleteActivityPhoto/{id}")
    fun deleteActivityPhoto(@PathVariable("id") id: Int): String {
        activityPhotoDao!!.deleteById(id)
        return "redirect:/sec/thirdphotos/photos"
    }

    /**
     * 编辑或添加
     */
    @GetMapping("/updateOraddphotos")
    fun getActivityPhoto(@RequestParam(required = false) id: Int?, model: Model): String {
        var photosData = ActivityPhotoData()
        if (id != null && id>0) {
            photosData.activityphoto = thirdPhotoService!!.getAllPhotoId(id)
        } else {
            photosData.activityphoto = ActivityPhoto()
        }
        model.addAttribute("photosData", photosData)
        return "sec/thirdphotos/updateOraddphotos"
    }

    /**
     * 保存添加或编辑活动相册信息
     */
    @RequestMapping(value = "/updateOraddphotos", method = arrayOf(RequestMethod.POST))
    @Transactional
    fun savePhotos(@ModelAttribute("activityphoto") activityphoto: ActivityPhoto): String {
        if (activityphoto.id == null || activityphoto.id == 0) {
            activityPhotoDao!!.insert(activityphoto)
        } else {
            activityPhotoDao!!.update(activityphoto)
        }
        return "redirect:/sec/thirdphotos/photos"
    }
}
