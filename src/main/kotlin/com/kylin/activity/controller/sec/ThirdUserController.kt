package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ThirdUserService
import com.kylin.activity.service.UserService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("sec/thirduser")
@SessionAttributes("user")
class ThirdUserController : BaseController() {

    @Autowired
    private val userService: ThirdUserService? = null

    @Autowired
    private val userDao: UserDao? = null

    /**
     * 取得用户信息
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    private fun getUser(@RequestParam("id") id: Int): User {
        return userService!!.getUser(id)
    }

    /**
     * 取得用户信息集合
     * @return
     */
    @RequestMapping(value = "/getUsers")
    @ResponseBody
    fun getUsers(): List<User> {
        return userService!!.getAllUsers()
    }

    /**
     * 取得会员信息集合
     */
    @RequestMapping(value = "/getMembers")
    @ResponseBody
    fun members(): List<User> {
        return userService!!.getMembers()
    }

    /**
     * 第三方查询用户信息
     */
    @CrossOrigin
    @RequestMapping(value = "/users", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun allUsers(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

//        var username = request.getParameter("username")
//        //所有用户
//        var users = userService!!.getAllUsersAndScores(start, end, username)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
//        model.addAttribute("users", users)
        return "sec/thirduser/users"
    }

    /**
     * 第三方异步查询
     * 用户信息
     */
    @CrossOrigin
    @RequestMapping(value = "/searchUsers", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @ResponseBody
    fun searchUsers(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var username = map["username"]
        var displayname = map["displayname"]
        var real_name = map["real_name"]
        var id_card = map["id_card"]
        var level = map["level"]
        var isMember = map["isMember"]

        //查询用户
        var items = userService!!.getAllUsersAndScores(start, end, username, displayname, real_name, id_card, level, isMember)
        var list = items.intoMaps()
        return list
    }


    /**
     * 第三方添加用户信息
     */
    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.GET))
    fun createUser(user: User, model: Model): String {
        var user = User()
        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/thirduser/create"
    }


    /**
     * 保存添加用户信息
     */
    @RequestMapping(value = "/saveUser", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    fun saveUser(@ModelAttribute("user") user: User, redirectAttributes: RedirectAttributes): String {
        var u = userService!!.getUser(user!!.username)
        if (u != null) {
            throw Exception("用户账号已存在!")
        }

        var coder = BCryptPasswordEncoder()
        user.password = coder.encode("123456")
        user.enabled = true
        user.created = DateUtil.date().toTimestamp()
        if (user.isReal) {
            user.realTime = DateUtil.date().toTimestamp()
        }
        userService!!.insert(user)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/thirduser/users"
    }

    /**
     * 第三方编辑用户信息
     */
    @RequestMapping(value = "/update/{id}", method = arrayOf(RequestMethod.GET))
    fun getUpdate(@PathVariable("id") id: Int, model: Model): String {
        val user = userService!!.getUser(id)
        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/thirduser/update"
    }

    /**
     * 保存编辑用户信息
     */
    @PostMapping(value = "/update")
    fun postUpdate(model: Model, user: User, redirectAttributes: RedirectAttributes): String {
        model.addAttribute("title", "个人信息")
        userDao!!.update(user)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/thirduser/users"
    }

    /**
     * 第三方删除用户信息
     */
    @CrossOrigin
    @RequestMapping(value = "/delete/{id}", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun delete(@PathVariable("id") id: Int, model: Model, redirectAttributes: RedirectAttributes): String {
        userService!!.deleteById(id)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/thirduser/users"
    }


    /**
     * 查询会员信息
     */
    @CrossOrigin
    @RequestMapping(value = "/members", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun member(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank()) {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank()) {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)

        return "sec/thirduser/members"
    }

    /**
     * 注册会员信息
     */
    @GetMapping("/registermember")
    fun registerMember(request: HttpServletRequest, model: Model): String {
        var user = userService!!.getCurrentUserInfo()
        model.addAttribute("user", user)
        //将前页面URL添加至模型数据中
        model.addAttribute("current_url", request.getAttribute("current_url"))
        return "sec/thirduser/registermember"
    }

    /**
     * 保存会员认证信息
     */
    @PostMapping("/registermember")
    @Transactional
    fun registerMember(@ModelAttribute("user") user: User,
                       request: HttpServletRequest,
                       @ModelAttribute("current_url") current_url: String,
                       redirectAttributes: RedirectAttributes,
                       model: Model): String {
        //获取用户的基本注册信息
        var member = userService!!.getUser(user.id!!)
        member.level = 1 //会员用户
        member.realTime = DateUtil.date().toTimestamp()
        member.isReal = true //认证通过
        member.realName = user.realName
        member.idCard = user.idCard
        member.gender = user.gender
        //更新成为会员
        userService!!.update(member)

        //将注册认证后的会员信息存到session中
        this.sessionUser = member

        model.addAttribute("user", member)
        redirectAttributes.addFlashAttribute("globalMessage", "注册会员成功！")
        return "redirect:" + current_url
    }

}