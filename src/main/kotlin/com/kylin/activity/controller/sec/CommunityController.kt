package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.service.CommunityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("sec/community")
@SessionAttributes("communities")
class CommunityController : BaseController() {
    @Autowired
    private val communityService: CommunityService? = null

    @Autowired
    private val communityDao: CommunityDao? = null
    /**
     * 获取团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/communities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun afterCommunity(model: Model, request: HttpServletRequest): String {
        var name = request.getParameter("name")
        model.addAttribute("communities", communityService!!.queryCommunity(name))
        return "sec/community/communities"
    }



    /**
     * 添加团体信息
     */
    @GetMapping("/addcommunity")
    fun addCommunity(model: Model): String {
        var addCommunity = Community()
        model.addAttribute("communities", addCommunity)
        return "sec/community/addcommunity"
    }

    /**
     * 保存团体信息
     */
    @PostMapping("/saveCommunity")
    @Throws(Exception::class)
    fun saveCommunity(@ModelAttribute("community") community: Community): String {
        communityService!!.createCommunity(community)
        return "redirect:/sec/community/communities"
    }

    /**
     * 删除团体信息
     */
    @CrossOrigin
    @GetMapping("/deleteCommunity/{id}")
    fun deleteCommunity(@PathVariable id: Int): String {
        communityService!!.deleteCommunity(id)
        return "redirect:/sec/community/communities"
    }

    /**
     * 编辑团体信息
     */
    @GetMapping("/updatecommunity/{id}")
    fun editCommunity(@PathVariable("id")id:Int,model: Model):String{
       var community=communityService!!.editCommunity(id)
        model.addAttribute("communities",community)
       return "sec/community/updatecommunity"
    }
    /**
     * 修改团体信息
     */
    @PostMapping("/updatecommunity")
    fun updateCommunity(community: Community):String{
      communityDao!!.update(community)
        return "redirect:/sec/community/communities"
    }

}