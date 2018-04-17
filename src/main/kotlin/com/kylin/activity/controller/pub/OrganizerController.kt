package com.kylin.activity.controller.pub

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("pub/activity")
class OrganizerController {

    @GetMapping("/organizer")
    fun organizer():String{
       return "pub/activity/organizer"
    }

}