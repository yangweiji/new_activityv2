package com.kylin.activity.controller.pub

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
    private val posterService: PosterService? = null

    /**
     * 公共服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 首页点击‘更多’标签
     * 显示全部海报信息
     * @param posterType 海报类型
     */
    @GetMapping("/allposter/{posterType}")
    fun getAllPoster(@PathVariable("posterType")posterType:String, model: Model):String{
        var allPoster=posterService!!.getAllPoster(posterType)
        for (r in allPoster) {
            if (r.get("avatar") != null) {
                r.setValue(r.fieldsRow().field("avatar", String::class.java), commonService!!.getDownloadUrl(r.get("avatar").toString()))
            }
        }
        model.addAttribute("posterType",posterType)
        model.addAttribute("posters",allPoster)
        return "pub/poster/allposter"
    }


}