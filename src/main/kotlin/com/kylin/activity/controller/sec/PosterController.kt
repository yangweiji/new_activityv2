package com.kylin.activity.controller.sec

import com.kylin.activity.databases.tables.pojos.Poster
import com.kylin.activity.service.PosterService
import com.kylin.activity.util.CommonService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

data class PostersData(
        var poster: Poster? = null
)

@Controller
@RequestMapping("sec/poster")
class PosterController {


    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 海报服务
     */
    @Autowired
    private val posterService: PosterService? = null

    /**
     * 跳转至海报管理界面
     */
    @RequestMapping(value = "/posters", method = [RequestMethod.POST, RequestMethod.GET])
    fun queryPoster(): String {
        return "sec/poster/posters"
    }


    /**
     * 异步获取海报信息集合
     * @param map 海报集合
     * @return 海报信息集合
     */
    @RequestMapping(value = "/getPosters", method = [RequestMethod.POST, RequestMethod.GET])
    @CrossOrigin
    @ResponseBody
    fun queryPosterItems(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var title = map["title"]
        var posterType = map["posterType"]
        var items = posterService!!.queryPosterItems(title, posterType)
        var list = items.intoMaps()
        return list
    }

    /**
     * 添加海报或编辑海报信息
     * @param id 海报id
     * @param model 存放海报信息的数据模型
     */
    @RequestMapping(value = "/poster", method = [RequestMethod.GET])
    fun poster(@RequestParam(required = false)id:Int?,model: Model): String {
        var postersData=PostersData()
        if(id!=null&&id>0){
           postersData.poster=posterService!!.getPoster(id)
        }else{
            postersData.poster= Poster()
        }
        model.addAttribute("postersData",postersData)
        return "sec/poster/poster"
    }

    /**
     * 保存海报信息
     * @param poster 海报信息
     * @return 重定向至海报信息界面
     */
    @RequestMapping(value="/savePoster",method = [RequestMethod.POST])
    fun savePoster(@ModelAttribute("poster") poster: Poster?,
                   model: Model):String{

        if(poster!!.id!=null&&poster!!.id>0){
            var title=poster.title
            var avatar=poster.avatar
            var mobileAvatar=poster.mobileAvatar
            //mobileAvatar为空时，使用avatar的值
            if(mobileAvatar==null){
                mobileAvatar=avatar
            }
            var link=poster.link
            var activityId=poster.activityId
            var posterType=poster.posterType
            var show=poster.show
            var sequence=poster.sequence
            posterService!!.updatePoster(title,avatar,mobileAvatar,link,activityId,posterType,show,sequence,poster.id)
        }else{
            //检验海报是否已存在
            var posterTitle=posterService!!.getPosterTitle(poster.title)
            if(posterTitle!=null){
                return "海报：【${poster.title}】已存在！"
            }
            //新建海报信息
            var posters=Poster()
            posters.title=poster.title
            posters.avatar=poster.avatar
            posters.mobileAvatar=poster.mobileAvatar
            posters.link=commonService!!.getDownloadUrl(posters.avatar)
            posters.activityId=poster.activityId
            posters.created=DateUtil.date().toTimestamp()
            posters.posterType=poster.posterType
            posters.show=poster.show
            posters.sequence=poster.sequence
            posterService!!.insertPoster(posters)
        }
        return "redirect:/sec/poster/posters"
    }


    /**
     * 删除海报信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deletePoster",method = [RequestMethod.POST])
    @ResponseBody
    fun deletePoster(request:HttpServletRequest):Any?{
        var posterId=if(request.getParameter("posterId")!=null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.deletePoster(posterId)
        return true
    }

    /**
     * 平台中心海报管理
     * 隐藏海报
     */
    @CrossOrigin
    @RequestMapping(value = "/showPoster",method = [RequestMethod.POST])
    @ResponseBody
    fun showPoster(request:HttpServletRequest):Any?{
        var posterId=if(request.getParameter("posterId")!=null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.showPoster(posterId)
        return true
    }

    /**
     * 平台中心海报管理
     * 显示海报
     */
    @CrossOrigin
    @RequestMapping(value = "/displayPoster",method = [RequestMethod.POST])
    @ResponseBody
    fun displayPoster(request:HttpServletRequest):Any?{
        var posterId=if(request.getParameter("posterId")!=null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.displayPoster(posterId)
        return true
    }
}