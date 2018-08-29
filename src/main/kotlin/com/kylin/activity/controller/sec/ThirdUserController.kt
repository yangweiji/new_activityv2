package com.kylin.activity.controller.sec

import com.kylin.activity.config.ActivityProperties
import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.User
import com.kylin.activity.service.ThirdUserService
import com.kylin.activity.util.LogUtil
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

/**
 * 第三方团体组织用户管理控制器
 * @author Richard C. Hu
 */
@Controller
@RequestMapping("sec/community/thirduser")
@SessionAttributes("user")
class ThirdUserController : BaseController() {

    /**
     * 团体组织用户服务
     */
    @Autowired
    private val userService: ThirdUserService? = null

    /**
     * 全局配置信息
     */
    @Autowired
    private val activityProperties: ActivityProperties? = null

    /**
     * 取得用户信息
     * @param id
     * @return 用户信息
     */
    @RequestMapping("/getUser")
    @ResponseBody
    private fun getUser(@RequestParam("id") id: Int): User {
        return userService!!.getUser(id)
    }

    /**
     * 取得全部用户信息集合
     * @return 用户列表信息
     */
    @RequestMapping(value = "/getUsers")
    @ResponseBody
    fun getUsers(): List<User> {
        return userService!!.getAllUsers()
    }

    /**
     * 取得会员信息集合
     * @return 会员用户列表信息
     */
    @RequestMapping(value = "/getMembers")
    @ResponseBody
    fun getMembers(): List<User> {
        return userService!!.getMembers()
    }

    /**
     * 第三方查询用户信息
     * @param request: 请求参数
     * @param model: 模型
     * @return 用户查询页面
     */
    @CrossOrigin
    @RequestMapping(value = "/users", method = [RequestMethod.POST, RequestMethod.GET])
    fun users(request: HttpServletRequest, model: Model): String {
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
        return "sec/community/thirduser/users"
    }

    /**
     * 第三方异步查询
     * 用户信息
     * @param map: 查询参数
     * @return 用户列表信息
     */
    @CrossOrigin
    @RequestMapping(value = "/searchUsers", method = [RequestMethod.POST, RequestMethod.GET])
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
        var is_black=map["is_black"]
        var role = map["role"]
        //查询用户
        var items = userService!!.getCommunityUsersAndScores(this.sessionCommunity.id, start, end, username, displayname, real_name, id_card, level, isMember,is_black,role)
        return items.intoMaps()
    }


    /**
     * 第三方添加用户信息
     * @param user: 用户信息
     * @param model: 模型
     * @return 添加用户页面
     */
    @RequestMapping(value = "/createUser", method = [RequestMethod.GET])
    fun createUser(user: User, model: Model): String {
        var user = User()
        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/community/thirduser/create"
    }


    /**
     * 保存添加用户信息
     * @param user: 用户信息
     * @param redirectAttributes：重定向属性
     * @return 保存用户页面
     */
    @RequestMapping(value = "/saveUser", method = [RequestMethod.POST])
    @Transactional
    @Throws(Exception::class)
    fun saveUser(@ModelAttribute("user") user: User,redirectAttributes: RedirectAttributes
                 , model: Model): String {
        var u = userService!!.getUser(user!!.username)
        if (u != null) {
            //如果用户手机号已存在，则添加至当前团体组织中
            user.id = u.id
            user.username = u.username
        } else {
            //添加用户
            var coder = BCryptPasswordEncoder()
            user.password = coder.encode(activityProperties!!.defaultPassword)
            user.enabled = true
            user.created = DateUtil.date().toTimestamp()
            if (user.isReal) {
                user.realTime = DateUtil.date().toTimestamp()
            }

            userService!!.insert(user)
            LogUtil.printLog("添加用户成功, 用户ID: ${user.id}")
        }

        //检查关联关系是否已经存在
        var communityExistUser = userService!!.getCommunityUser(this.sessionCommunity.id, user.id)
        if (communityExistUser != null) {
            model.addAttribute("errorMessage", "用户: ${user.username} 已在当前团体组织下，无需重复添加！")
            return "sec/community/thirduser/create"
        }

        //将用户关联到当前团体组织
        var communityUser = CommunityUser()
        communityUser.communityId = this.sessionCommunity.id
        communityUser.userId = user.id
        communityUser.role = user.role
        communityUser.level = user.level
        communityUser.created = DateUtil.date().toTimestamp()
        communityUser.isBlack=false
        userService!!.insertCommunityUser(communityUser)
        LogUtil.printLog("添加团体组织用户关联数据成功, 关联ID: ${communityUser.id}")

        if (u == null) {
            //将用户平台角色设置为NULL
            user.role = null
            //将用户平台会员年度设置为NULL
            user.level = null
            userService!!.update(user)
            LogUtil.printLog("更新用户信息成功, 用户ID: ${user.id}")
        }

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/community/thirduser/users"
    }

    /**
     * 第三方编辑用户信息
     * @param id: 用户ID
     * @param model: 模型
     * @return 编辑用户页面
     */
    @RequestMapping(value = "/update/{id}", method = [RequestMethod.GET])
    fun update(@PathVariable("id") id: Int, model: Model): String {
        val user = userService!!.getUser(id)
        var communityUser = userService!!.getCommunityUser(this.sessionCommunity.id, id)

        //取得用户在当前团体组织中的角色和会员年度信息
        user.role = communityUser!!.role
        user.level = communityUser!!.level

        model.addAttribute("user", user)
        model.addAttribute("roles", userService!!.getRoles().values)
        return "sec/community/thirduser/update"
    }

    /**
     * 保存编辑用户信息
     * @param user: 用户对象
     * @param redirectAttributes: 重定向属性
     */
    @PostMapping(value = "/updateUser")
    @Transactional
    fun updateUser(model: Model, user: User, redirectAttributes: RedirectAttributes): String {
        //用户的角色、会员年度
        var communityUser = userService!!.getCommunityUser(this.sessionCommunity.id, user.id)
        if (user == null) {
            //将用户关联到当前团体组织
            communityUser = CommunityUser()
            communityUser.communityId = this.sessionCommunity.id
            communityUser.userId = user.id
            communityUser.role = user.role
            communityUser.level = user.level
            communityUser.created = DateUtil.date().toTimestamp()
            userService!!.insertCommunityUser(communityUser)
            LogUtil.printLog("添加团体组织用户关联数据成功, 关联ID: ${communityUser.id}")
        } else {
            communityUser!!.role = user.role
            communityUser!!.level = user.level
            userService!!.updateCommunityUser(communityUser)
            LogUtil.printLog("更新团体组织用户关联数据成功, 关联ID: ${communityUser.id}")
        }

        var u = userService!!.getUser(communityUser!!.userId)
        if (u != null) {
            //不更新平台管理角色及平台会员年度
            user.role = u.role
            user.level = u.level
            userService!!.update(user)
            LogUtil.printLog("更新用户信息成功, 用户ID: ${user.id}")
        }

        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/community/thirduser/users"
    }

    /**
     * 第三方删除用户信息
     * @param id: 用户ID
     * @param model: 模型
     * @return 删除用户页面
     */
    @CrossOrigin
    @RequestMapping(value = "/delete", method = [RequestMethod.POST])
    @ResponseBody
    @Transactional
    fun delete(@RequestParam("id") id: Int, model: Model): Any {
        //删除团体组织用户关联信息
        userService!!.deleteCommunityUser(this.sessionCommunity.id, id)
        //删除用户信息
        //userService!!.deleteById(id)

        return true
    }

    /**
     * 查询会员信息
     * @param request: 请求参数
     * @param model: 模型
     * @return 查询会员页面
     */
    @CrossOrigin
    @RequestMapping(value = "/members", method = [RequestMethod.GET, RequestMethod.POST])
    fun members(request: HttpServletRequest, model: Model): String {
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

        return "sec/community/thirduser/members"
    }

    /**
     * 注册会员信息
     * @param request: 请求参数
     * @param model: 模型
     * @return 注册会员页面
     */
    @GetMapping("/registerMember")
    fun registerMember(request: HttpServletRequest, model: Model): String {
        var user = userService!!.getCurrentUserInfo()
        model.addAttribute("user", user)
        //将前页面URL添加至模型数据中
        model.addAttribute("current_url", request.getAttribute("current_url"))

        return "sec/community/thirduser/registermember"
    }

    /**
     * 保存会员认证信息
     * @param user: 用户信息
     * @param request: 请求参数
     * @param current_url: 当前URL
     * @param model: 模型
     * @return 注册会员页面
     */
    @PostMapping("/registerMember")
    @Transactional
    fun registerMember(request: HttpServletRequest,
                       @ModelAttribute("current_url") current_url: String,
                       redirectAttributes: RedirectAttributes,
                       model: Model): String {
        var user=this.sessionUser
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

        return "redirect:$current_url"
    }


    /**
     * 移除黑名单
     */
    @CrossOrigin
    @RequestMapping(value = "/removeBlack", method = [RequestMethod.POST])
    @ResponseBody
    fun removeBlack(request: HttpServletRequest): Any? {
        var userId = if (request.getParameter("userId") != null)
            request.getParameter("userId").toInt() else 0
        userService!!.removeBlack(userId)
        return true
    }


    /**
     * 加入黑名单
     */
    @CrossOrigin
    @RequestMapping(value = "/addBlack", method = [RequestMethod.POST])
    @ResponseBody
    fun addBlack(request: HttpServletRequest): Any? {
        var userId = if (request.getParameter("userId") != null)
            request.getParameter("userId").toInt() else 0
        userService!!.addBlack(userId)
        return true
    }
}