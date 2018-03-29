package com.kylin.activity.controller.pub

import com.kylin.activity.service.UserService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("pub")
class ErrorController {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val util: KylinUtil? = null

    @GetMapping("/error/{code}")
    fun get(@PathVariable("code") code: Int, model: Model): String {
        var message = when(code){
            20 -> "您没有权限进行此操作"
            else -> "未知错误"
        }

        model.addAttribute("message", message)
        return "pub/error"
    }


}