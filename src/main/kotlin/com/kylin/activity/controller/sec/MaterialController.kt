package com.kylin.activity.controller.sec

import com.kylin.activity.config.ActivityProperties
import com.kylin.activity.databases.tables.pojos.MaterialLibrary
import com.kylin.activity.service.MaterialService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

/**
 * Created by 9kylin on 2018-08-16.
 */
@Controller
@RequestMapping("sec/admin/material")
class MaterialController {

    @Autowired
    private val materialService: MaterialService? = null

    /**
     * 活动配置
     */
    @Autowired
    private val activityProperties: ActivityProperties? = null

    /**
     * 跳转至素材管理界面
     */
    @RequestMapping(value = "/materials", method = [RequestMethod.POST, RequestMethod.GET])
    fun materials(): String {
        return "sec/admin/material/materials"
    }


    /**
     * 异步获取海报信息集合
     * @param map 海报集合
     * @return 海报信息集合
     */
    @RequestMapping(value = "/getMaterialCatgories", method = [RequestMethod.POST, RequestMethod.GET])
    @CrossOrigin
    @ResponseBody
    fun getMaterialCatgories(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var items = materialService!!.getMaterialCategories()
        return items.intoMaps()
    }

    /**
     * 异步获取海报信息集合
     * @param map 海报集合
     * @return 海报信息集合
     */
    @RequestMapping(value = "/getMaterials", method = [RequestMethod.POST, RequestMethod.GET])
    @CrossOrigin
    @ResponseBody
    fun getMaterials(@RequestBody(required = false) map: Map<String, String>): Any {
        var category = map["category"]
        var index = map["index"]!!.toInt()
        var pageSize = map["pageSize"]!!.toInt()


        //记录记录总数
        var totalCount = materialService!!.getMaterialsCount(category)
        //分页获取数据
        var items = materialService!!.getMaterials(category, index*pageSize, pageSize)

        //返回总数和记录列表
        return mapOf("items" to items.intoMaps(), "totalCount" to totalCount)
    }

    /**
     * 添加海报或编辑海报信息
     * @param id 海报id
     * @param model 存放海报信息的数据模型
     */
    @RequestMapping(value = "/material", method = [RequestMethod.GET])
    fun material(@RequestParam(required = false) id: Int?, model: Model): String {
        var item: MaterialLibrary
        if (id != null && id > 0) {
            item = materialService!!.getMaterial(id)
        } else {
            item = MaterialLibrary()
            item.created = DateUtil.date().toTimestamp()
        }
        model.addAttribute("material", item)
        return "sec/admin/material/material"
    }

    /**
     * 保存信息
     * @param material 素材信息
     * @return 重定向至素材信息界面
     */
    @RequestMapping(value = "/save", method = [RequestMethod.POST])
    fun save(@ModelAttribute("material") material: MaterialLibrary,
                   model: Model, redirectAttributes: RedirectAttributes): String {

        if (material.id != null) {
            //编辑
            redirectAttributes.addFlashAttribute("globalMessage", "素材：【${material.name}】编辑成功！")
            materialService!!.update(material)
        } else {
            material.created = DateUtil.date().toTimestamp()
            materialService!!.insert(material)
            redirectAttributes.addFlashAttribute("globalMessage", "海报：【${material.name}】添加成功！")
        }
        return "redirect:/sec/admin/material/materials"
    }

    /**
     * 删除信息
     */
    @CrossOrigin
    @RequestMapping(value = "/delete", method = [RequestMethod.POST])
    @ResponseBody
    fun delete(request: HttpServletRequest): Any? {
        var id = if (request.getParameter("id") != null)
            request.getParameter("id").toInt() else 0
        materialService!!.delete(id)
        return true
    }

    /**
     * 图片保存至素材库
     */
    @CrossOrigin
    @RequestMapping(value = "/saveToLibrary", method = [RequestMethod.POST])
    @ResponseBody
    fun saveToLibrary(request: HttpServletRequest): Any? {
        var name = request.getParameter("name")
        var item = MaterialLibrary()
        item.created = DateUtil.date().toTimestamp()
        item.sequence = 0
        item.category = "未分组"
        item.name = name
        materialService!!.insert(item)
        return true
    }
}