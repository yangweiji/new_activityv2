package com.kylin.activity.controller.pub

import com.kylin.activity.service.UserService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("pub")
@SessionAttributes("username")
class RegisterController {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val util: KylinUtil? = null

    @GetMapping("/register")
    fun get(model: Model): String {
        return "pub/register"
    }

    @PostMapping("/register")
    fun post(request: HttpServletRequest, response: HttpServletResponse, username: String, password: String, code: String, model: Model): String {
        model.addAttribute("username", username)
        var result = userService!!.register(username, password, code)
        return if(result == "success"){
            "redirect:" + util!!.redirectAfterLogin(request,response)
        } else {
            "redirect:/pub/register?$result"
        }
    }

    @GetMapping("/changepassword")
    fun getChangePassword(model: Model): String {
        return "pub/changePassword"
    }

    @PostMapping("/changepassword")
    fun postChangePassword(request: HttpServletRequest, response: HttpServletResponse, username: String, password: String, code: String, model: Model): String {
        model.addAttribute("username", username)
        var result = userService!!.changePassword(username, password, code)
        return if(result == "success"){
            "redirect:/login?changepasswordok"
        } else {
            "redirect:/pub/changepassword?" + result
        }
    }
}