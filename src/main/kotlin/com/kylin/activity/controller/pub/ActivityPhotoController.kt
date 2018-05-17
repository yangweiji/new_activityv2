package com.kylin.activity.controller.pub

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.ActivityFavorite
import com.kylin.activity.service.ActivityPhotoService
import com.kylin.activity.service.ActivityService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.text.ParseException

@Controller
@RequestMapping("pub/activityphoto")
@SessionAttributes("activityphoto")
class ActivityPhotoController : BaseController() {
    @Autowired
    private var activityPhotoService: ActivityPhotoService? = null
    @Autowired
    private var commonService: CommonService? = null
    @Autowired
    private var activityService: ActivityService? = null

    /**
     * 首页活动相册
     */
    @RequestMapping(value = "/photo", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activityPhoto(model: Model,@MatrixVariable(defaultValue = "0")page:Int?): String {
        var activityPhoto = activityPhotoService!!.getActivityPhoto(this.sessionCommunity.id)
        for (r in activityPhoto) {
            if (r.get("picture") != null) {
                r.setValue(r.fieldsRow().field("picture", String::class.java), commonService!!.getDownloadUrl(r.get("picture").toString()))
            }
        }
        model.addAttribute("activityphoto", activityPhoto)

      /*  //设置记录数
        activityPhotoService!!.size=PAGE_SIZE
        //设置起始的记录位置
        activityPhotoService!!.page=page!!

        var photopages=activityPhotoService!!.getPhotoPage()
        val pageable = PageRequest(page!!, activityPhotoService!!.size )
        val pageImpl = PageImpl(photopages, pageable, activityPhotoService!!.activityphotoCount)
        model.addAttribute("pageImpl", pageImpl)
        model.addAttribute("first", pageImpl.isFirst)
        model.addAttribute("last", pageImpl.isLast)

        model.addAttribute("page",page)
        model.addAttribute("photopages",photopages)*/
        return "pub/activityphoto/photo"
    }

    /**
     * 活动相册详情
     */
    @CrossOrigin
    @RequestMapping(value = "/detail/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun activityPhotoDetail(@PathVariable("id") id: Int, model: Model): String {
        var detail = activityPhotoService!!.getActivityPhotoDetail(id)
        for (r in detail) {
            if (r.get("picture") != null) {
                r.setValue(r.fieldsRow().field("picture", String::class.java), commonService!!.getDownloadUrl(r.get("picture").toString()))
            }
        }
        model.addAttribute("activityphoto", detail)
        return "pub/activityphoto/detail"
    }

   /* @RequestMapping(value = "/photo", method = arrayOf(RequestMethod.GET,RequestMethod.POST))
    fun photoPage(@MatrixVariable(defaultValue = "0")page:Int?,model: Model): String {
        //设置记录数
        activityPhotoService!!.size=PAGE_SIZE
        //设置起始的记录位置
        activityPhotoService!!.page=page!!

        var photopages=activityPhotoService!!.getPhotoPage(this.sessionCommunity.id)
        val pageable = PageRequest(page!!, activityPhotoService!!.size )
        val pageImpl = PageImpl(photopages, pageable, activityPhotoService!!.activityphotoCount)
        model.addAttribute("pageImpl", pageImpl)
        model.addAttribute("first", pageImpl.isFirst)
        model.addAttribute("last", pageImpl.isLast)

        model.addAttribute("page",page)
        model.addAttribute("photopages",photopages)

        return "pub/activityphoto/photo"
    }*/
}