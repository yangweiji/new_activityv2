package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.daos.CommunityDao
import com.kylin.activity.databases.tables.pojos.Community
import com.kylin.activity.service.CommunityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

data class CommunitiesData(
        var community: Community?=null
)


@Controller
@RequestMapping("sec/community")
class CommunityController : BaseController() {
    @Autowired
    private val communityService: CommunityService? = null

    @Autowired
    private val communityDao: CommunityDao? = null

    /**
     * 查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/communities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun afterCommunity(): String {
        return "sec/community/communities"
    }


    /**
     * 异步查询团体信息
     */
    @CrossOrigin
    @RequestMapping(value = "/getCommunities", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    fun getCommunities(@RequestBody(required = false)map:Map<String,String>): List<Any> {
         var name=map["name"]
         var items=communityService!!.queryCommunity(name)
         var list=items.intoMaps()
         return list
    }

    /**
     * 编辑或添加团体信息
     */
    @GetMapping("/updateOraddcommunity")
    fun updateOraddcommunity(@RequestParam(required = false)id:Int?,model: Model): String {
        var communitiesData=CommunitiesData()
        if(id!=null&&id>0){
            communitiesData.community=communityService!!.getCommunityId(id)
        }else{
            communitiesData.community= Community()
        }
        model.addAttribute("communitiesData",communitiesData)
        return "sec/community/updateOraddcommunity"
    }

    /**
     * 保存编辑或添加团体信息
     */
    @PostMapping("/updateOraddcommunity")
    @Transactional
    fun saveCommunity(@ModelAttribute("community") community: Community): String {
        if(community.id!=null&&community.id>0){
            communityDao!!.update(community)
        }else{
            communityDao!!.insert(community)
        }
        return "redirect:/sec/community/communities"
    }
}