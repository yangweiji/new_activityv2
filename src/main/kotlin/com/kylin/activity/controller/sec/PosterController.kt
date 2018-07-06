package com.kylin.activity.controller.sec

import com.kylin.activity.databases.tables.pojos.Poster
import com.kylin.activity.service.ActivityService
import com.kylin.activity.service.PosterService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("sec/admin/poster")
class PosterController {

    /**
     * 海报服务
     */
    @Autowired
    private val posterService: PosterService? = null

    @Autowired
    private val activityService: ActivityService? = null

    /**
     * 跳转至海报管理界面
     */
    @RequestMapping(value = "/posters", method = [RequestMethod.POST, RequestMethod.GET])
    fun queryPoster(): String {
        return "sec/admin/poster/posters"
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
    fun poster(@RequestParam(required = false) id: Int?, model: Model): String {
        var poster: Poster
        if (id != null && id > 0) {
            poster = posterService!!.getPoster(id)
        } else {
            poster = Poster()
            poster.show = true
            poster.posterType = "n1"
            poster.created = DateUtil.date().toTimestamp()
        }
        model.addAttribute("poster", poster)
        return "sec/admin/poster/poster"
    }

    /**
     * 保存海报信息
     * @param poster 海报信息
     * @return 重定向至海报信息界面
     */
    @RequestMapping(value = "/savePoster", method = [RequestMethod.POST])
    fun savePoster(@ModelAttribute("poster") poster: Poster,
                   model: Model, redirectAttributes: RedirectAttributes): String {

        //链接和活动编号，至少填写一项
        if (poster.activityId == null && poster.link.isNullOrBlank()) {
            model.addAttribute("errorMessage", "链接和活动编号，至少填写其中一项！")
            return "/sec/admin/poster/poster"
        }

        //如果用户输入了活动编号，判断海报活动是否存在
        if (poster.activityId != 0) {
            var activity = activityService!!.getActivity(poster.activityId)
            if (activity == null) {
                model.addAttribute("errorMessage", "活动编号：[${poster.activityId}] 无效！")
                return "/sec/admin/poster/poster"
            }
        }

        if (poster.id != null) {
            //编辑
            redirectAttributes.addFlashAttribute("globalMessage", "海报：【${poster.title}】编辑成功！")
            posterService!!.update(poster)
        } else {
            //检验海报标题是否已存在
            if (posterService!!.getPosterTitle(poster.title) != null) {
                model.addAttribute("errorMessage", "海报：【${poster.title}】名称重复！")
                return "/sec/admin/poster/poster"
            }

            poster.created = DateUtil.date().toTimestamp()
            posterService!!.insertPoster(poster)
            redirectAttributes.addFlashAttribute("globalMessage", "海报：【${poster.title}】添加成功！")
        }
        return "redirect:/sec/admin/poster/posters"
    }

    /**
     * 删除海报信息
     */
    @CrossOrigin
    @RequestMapping(value = "/deletePoster", method = [RequestMethod.POST])
    @ResponseBody
    fun deletePoster(request: HttpServletRequest): Any? {
        var posterId = if (request.getParameter("posterId") != null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.deletePoster(posterId)
        return true
    }

    /**
     * 平台中心海报管理
     * 隐藏海报
     */
    @CrossOrigin
    @RequestMapping(value = "/showPoster", method = [RequestMethod.POST])
    @ResponseBody
    fun showPoster(request: HttpServletRequest): Any? {
        var posterId = if (request.getParameter("posterId") != null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.showPoster(posterId)
        return true
    }

    /**
     * 平台中心海报管理
     * 显示海报
     */
    @CrossOrigin
    @RequestMapping(value = "/displayPoster", method = [RequestMethod.POST])
    @ResponseBody
    fun displayPoster(request: HttpServletRequest): Any? {
        var posterId = if (request.getParameter("posterId") != null)
            request.getParameter("posterId").toInt() else 0
        posterService!!.displayPoster(posterId)
        return true
    }
}