package com.kylin.activity.controller.pub

import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Created by 9kylin on 2018-06-12.
 * 微信相册服务
 */
@RestController
@RequestMapping("pub/wx/photo")
class WxPhotoController {

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
     * 活动服务
     */
    @Autowired
    private val activityService: ActivityService? = null


    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null


    /**
     * 微信首页活动相册信息
     * @param communityId 团体编号
     * @return 该团体下所有的相册信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getPhotos", method = [RequestMethod.GET, RequestMethod.POST])
    fun getActivityPhotos(@RequestParam(required = false) communityId: Int?): Any {
        var communityId = if (communityId == null) 0 else communityId!!
        //依据团体组织id获取该团体的所有活动相册信息
        var photos = activityPhotoService!!.getActivityPhotoItemsByCommunity(communityId)

        var photoItems = mutableListOf<MutableMap<String, Any?>>()
        for (photo in photos) {
            var map = mutableMapOf<String, Any?>()
            var picture: String? = null
            if (photo["picture"] != null) {
                picture = commonService!!.getDownloadUrl(photo.get("picture", String::class.java), "middle")
            }
            map["id"] = photo.get("id", Int::class.java)
            map["activity_id"] = photo.get("activity_id", Int::class.java)
            map["description"] = photo.get("description").toString()
            map["created"] = util!!.fromNow(photo.get("created"))
            map["created_by"] = photo.get("created_by", Int::class.java)
            map["axtenal_url"] = photo.get("axtenal_url").toString()
            map["picture"] = picture
            photoItems.add(map)
        }
        return photoItems
    }

    /**
     * 活动详情页面，获取照片列表
     */
    @GetMapping("getPicturesByActivityId")
    fun getPicturesByActivityId(@RequestParam(required = true) activityId: Int): Any {
        var photo = activityPhotoService!!.getFirstActivityPhoto(activityId)
        var pictures = activityPhotoService!!.getPicturesByActivityId(activityId)
        return mapOf("description" to photo!!.description, "pictures" to pictures)
    }

}