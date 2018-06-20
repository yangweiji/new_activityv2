package com.kylin.activity.controller.pub

import com.kylin.activity.databases.Tables
import com.kylin.activity.service.PosterService
import com.kylin.activity.util.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("pub/poster")
class PostersController {

    /**
     * 海报服务
     */
    @Autowired
    private val posterService:PosterService?=null

    /**
     * 公共服务
     */
    @Autowired
    private val commonService:CommonService?=null

    /**
     * 海报详情信息
     * @param activityId 活动编号
     * @return 详情信息
     */
    @GetMapping("/posterdetail/{activityId}")
    fun posterDetail(@PathVariable("activityId") activityId: Int, model: Model): String {
        var poster = posterService!!.getPosterDetail(activityId)
        if(activityId>0) {
            if (poster["avatar"] != null) {
                poster.setValue(Tables.POSTER.AVATAR, commonService!!.getDownloadUrl(poster.get("avatar", String::class.java)))
            }
            model.addAttribute("poster", poster)
            return "pub/poster/posterdetail"
        }
        return "pub/poster/posterdetail"
    }


}