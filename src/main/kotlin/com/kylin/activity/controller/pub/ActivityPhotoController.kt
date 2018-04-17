package com.kylin.activity.controller.pub

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("pub/activity")
class ActivityPhotoController {

    @RequestMapping(value="/photo",method = arrayOf(RequestMethod.GET))
    fun activityPhoto(model:Model):String{
        return "pub/activity/photo"
    }
}