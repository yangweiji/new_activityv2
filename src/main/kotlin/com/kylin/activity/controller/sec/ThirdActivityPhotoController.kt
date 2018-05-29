package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.Tables
import com.kylin.activity.databases.tables.daos.ActivityDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoDao
import com.kylin.activity.databases.tables.daos.ActivityPhotoPictureDao
import com.kylin.activity.databases.tables.pojos.ActivityPhoto
import com.kylin.activity.databases.tables.pojos.ActivityPhotoPicture
import com.kylin.activity.databases.tables.pojos.Article
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

data class PicturesData(
        var activityPhotoPicture: ActivityPhotoPicture? = null
)

@Controller
@RequestMapping("sec/thirdactivity")
@SessionAttributes("user")
class ThirdActivityPhotoController : BaseController() {
    @Autowired
    private val activityDao: ActivityDao? = null

    @Autowired
    private val activityPhotoService: ActivityPhotoService? = null

    @Autowired
    private val activityPhotoPictureDao: ActivityPhotoPictureDao? = null

    @Autowired
    private val commonService: CommonService? = null

    @Autowired
    private val activityPhotoDao: ActivityPhotoDao? = null


    /**
     * 相册管理，根据活动相册查询图片信息
     * @param user 用户信息
     * @param activityId 活动相册外键:活动id
     * @param activityPhoto 活动相册信息
     * @return 图片信息集合
     */
    @RequestMapping(value = "/activityphotos", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun getActivityPhotos(@ModelAttribute user: User, @RequestParam(required = false) activityId: Int?, model: Model): String {
        //取得活动对应的唯一相册
        var activityPhoto = activityPhotoService!!.getOnlyOneActivityPhoto(activityId)
        //检查活动相册有无相册信息，无，则添加相册信息
        if (activityPhoto == null) {
            //创建活动对应的相册
            activityPhoto = ActivityPhoto(null, activityId, null, null, DateUtil.date().toTimestamp(), user!!.id, null)
            //添加到数据库
            var rs = activityPhotoService!!.insertActivityPhoto(activityPhoto)
//            activityPhotoDao!!.insert(activityPhoto)
            if (rs > 0) {
                //获取活动对应的相册
                activityPhoto = activityPhotoService!!.getOnlyOneActivityPhoto(activityId)
            }
        }

        //添加活动相册到模型数据中
        model.addAttribute("activityPhoto", activityPhoto)
        //获取相册对应的所有图片集合信息
        var photoPictureItems = activityPhotoService!!.getPhotoPicture(activityPhoto.id)

        for (r in photoPictureItems) {
            //更新活动图片的URL
            if (r.picture != null) {
                r.picture = commonService!!.getDownloadUrl(r.picture)
            }
        }

        //添加相册图片到模型数据中
        model.addAttribute("photoPictureItems", photoPictureItems)


        //添加图片
        model.addAttribute("activityId", activityId)
        var picturesData = PicturesData()
        var activityPhotoId = activityPhoto.id
        var picture = null
        var created = DateUtil.date().toTimestamp()
        var createdBy = user!!.id
        var order = null
        picturesData.activityPhotoPicture = ActivityPhotoPicture(null, activityPhotoId, picture, created, createdBy, order)
        model.addAttribute("picturesData", picturesData)
        return "sec/thirdactivity/activityphotos"
    }


    /**
     * 添加图片
     */
    /* @GetMapping("/activityphotos")
     fun uploadPictures(@RequestParam(required = false) activityId: Int?
                        , model: Model
                        , @ModelAttribute user: User): String {
         //将活动ID存储至数据模型中
         model.addAttribute("activityId", activityId)
         var activityPhoto = activityPhotoService!!.getOnlyOneActivityPhoto(activityId)

         var picturesData = PicturesData()
         var activityPhotoId = activityPhoto.id
         var picture = null
         var created = DateUtil.date().toTimestamp()
         var createdBy = user!!.id
         var order = null
         picturesData.activityPhotoPicture = ActivityPhotoPicture(null,activityPhotoId,picture,created,createdBy,order)
         model.addAttribute("picturesData", picturesData)
         return "sec/thirdactivity/activityphotos"
     }*/

    /**
     * 保存图片信息
     * @param activityphotopicture 图片信息
     */
    @PostMapping("/savePictures")
    fun savePictures(@ModelAttribute user: User, model: Model,
                     @ModelAttribute("activityphotopicture") activityphotopicture: ActivityPhotoPicture?, @RequestParam(required = false) activityId: Int?): String {
        var activityPhoto = activityPhotoService!!.getOnlyOneActivityPhoto(activityId)
        var activityphotopicture = ActivityPhotoPicture()
        activityphotopicture.id = null
        activityphotopicture.activityPhotoId = activityPhoto.id
        activityphotopicture.picture!=null
        activityphotopicture.created = DateUtil.date().toTimestamp()
        activityphotopicture.createdBy = user!!.id
        activityphotopicture.order = null
        activityPhotoPictureDao!!.insert(activityphotopicture)
        return "redirect:/sec/thirdactivity/activityphotos?activityId=" + activityId
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
