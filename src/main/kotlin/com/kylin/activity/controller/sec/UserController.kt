package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.UserService
import com.xiaoleilu.hutool.date.DateUtil
import org.jooq.DSLContext
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

/**
 * Created by 9kylin on 2017-12-01.
 */
@Controller
@RequestMapping("sec/admin/user")
@SessionAttributes("user")
class UserController : BaseController() {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val userDao: UserDao? = null

    @Autowired
    private val create: DSLContext? = null

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
     * 注册会员
     * @param model
     * *
     * @return
     */
    @GetMapping("/registermember")
    fun registerMember(request: HttpServletRequest,
                               model: Model): String {
        var user = userService!!.getCurrentUserInfo()
        model.addAttribute("user", user)

        //将前页面URL添加至模型数据中
        model.addAttribute("current_url", request.getAttribute("current_url"))
        return "sec/admin/user/registermember"
    }

    /**
     * 保存会员认证信息
     * @return
     */
    @RequestMapping(value = "/registermember", method = arrayOf(RequestMethod.POST))
    @Transactional
    fun registerMember(@ModelAttribute("user") user: User,request: HttpServletRequest,
            @ModelAttribute("current_url") current_url: String,
            redirectAttributes: RedirectAttributes,
            model: Model): String {
        //获取用户的基本注册信息
        var member = userService!!.getUser(user!!.id!!)
        member.level = 1 //会员用户
        member.realTime = DateUtil.date().toTimestamp()
        member.isReal = true //认证通过
        member.realName = user!!.realName
        member.idCard = user!!.idCard
        member.gender = user!!.gender
        //更新成为会员
        userService!!.update(member)

        //将注册认证后的会员信息存到session中
        this.sessionUser = member

        model.addAttribute("user", member)
        redirectAttributes.addFlashAttribute("globalMessage", "注册会员成功！")
        return "redirect:" + current_url
    }

    /**
     * 会员
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/members", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun member(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank())
        {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank())
        {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

//        var username = request.getParameter("username")
//        var displayname = request.getParameter("displayname")
//        var real_name = request.getParameter("real_name")
//        var id_card = request.getParameter("id_card")
//        var level = request.getParameter("level")
//
//        var members = userService!!.getMembers(start, end, username, displayname, real_name, id_card, level)

        model.addAttribute("start", start)
        model.addAttribute("end", end)
//        model.addAttribute("members", members)

        return "sec/admin/user/members"
    }

    /**
     * 用户
     * @param model
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/users", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun allUsers(request: HttpServletRequest, model: Model): String {
        var calendar = GregorianCalendar()
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var start = request.getParameter("start")
        if (start.isNullOrBlank())
        {
            //设置为月初
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            start = sdf.format(calendar.time)
        }

        var end = request.getParameter("end")
        if (end.isNullOrBlank())
        {
            //当日
            calendar = GregorianCalendar()
            end = sdf.format(calendar.time)
        }

        model.addAttribute("start", start)
        model.addAttribute("end", end)
        return "sec/admin/user/users"
    }

    /**
     * 查找用户RestController
     * @author Richard
     */
    @CrossOrigin
    @RequestMapping(value = "/searchUsers", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun searchUsers(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var start = map["start"]
        var end = map["end"]
        var username = map["username"]
        var displayname = map["displayname"]
        var real_name = map["real_name"]
        var id_card = map["id_card"]

        //查询用户
        var items = userService!!.getAllUsersAndScores(start, end, username, displayname, real_name, id_card)
        var list = items.intoMaps()
        return list
    }

    /**
     * 更新用户信息
     */
    @GetMapping("/update/{id}")
    fun getUpdate(@PathVariable("id") id: Int, model: Model): String {
        val user = userService!!.getUser(id)
        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/admin/user/update"
    }

//    @PostMapping("/update")
//    fun postUpdate(id:Int, level:Int?, role:String,
//                   displayname: String,email: String, gender:Int,
//                   bloodType:String,clothingSize:String,workCompany:String,occupation:String,
//                   emergencyContactName:String,emergencyContactMobile:String,isParty:Boolean,address:String,wechatId:String,model: Model): String {
//        model.addAttribute("title", "个人信息")
//
//        var user = userDao!!.fetchOneById(id)
//        user.level = level
//        user.role = role
//        user.displayname = displayname
//        user.gender = gender
//        user.email = email
//        user.bloodType = bloodType
//        user.clothingSize = clothingSize
//        user.workCompany = workCompany
//        user.occupation = occupation
//        user.emergencyContactName = emergencyContactName
//        user.emergencyContactMobile = emergencyContactMobile
//        user.isParty = isParty
//        user.address = address
//        user.wechatId = wechatId
//        userDao!!.update(user)
//        return "redirect:/sec/user/users"
//    }

    @PostMapping("/update")
    fun postUpdate(user: User, model: Model, redirectAttributes: RedirectAttributes): String {
        model.addAttribute("title", "个人信息")
        userDao!!.update(user)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/admin/user/users"
    }

    /**
     * 新建用户
     */
    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun createUser(user: User, model: Model): String {
        var user = User()
        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/admin/user/create"
    }

    /**
     * 保存用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "/saveUser", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    fun saveUser(   @ModelAttribute("user") user: User?,redirectAttributes: RedirectAttributes): String {
        var u = userService!!.getUser(user!!.username)
        if (u != null)
        {
            throw Exception("用户账号已存在!")
        }

        var coder = BCryptPasswordEncoder()
        user.password = coder.encode("123456")
        user.enabled = true
        user.created = DateUtil.date().toTimestamp()
        if (user.isReal)
        {
            user.realTime = DateUtil.date().toTimestamp()
        }
        userService!!.insert(user)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/admin/user/users"
    }

    /**
     * 删除活动信息
     */
    @CrossOrigin
    @RequestMapping(value = "/delete/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun delete(@PathVariable id: Int,
               model: Model,
               redirectAttributes: RedirectAttributes): String {
        userService!!.deleteById(id)
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/admin/user/users"
    }
}
