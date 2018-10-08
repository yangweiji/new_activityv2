package com.kylin.activity.controller.sec

import com.kylin.activity.controller.BaseController
import com.kylin.activity.databases.tables.pojos.TrainingCamp
import com.kylin.activity.service.ThirdTrainingCampService
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * @Auther: Administrator
 * @Date: 2018/10/8 12:01
 * @Description:
 */
@Controller
@RequestMapping("sec/community/thirdactivity")
class ThirdTrainingCamp : BaseController() {
    @Autowired
    private val trainingCampService: ThirdTrainingCampService? = null

    /**
     * 查询训练营信息
     */
    @CrossOrigin
    @RequestMapping(value = "/trainingcamp", method = [RequestMethod.GET, RequestMethod.POST])
    fun trainingcamps(): String {
        return "sec/community/thirdactivity/trainingcamp"
    }

    /**
     * 异步查询训练营信息
     */
    @CrossOrigin
    @RequestMapping(value = "/searchTrainingCamp", method = [RequestMethod.POST, RequestMethod.GET])
    @ResponseBody
    fun getTrainingcamps(@RequestBody(required = false) map: Map<String, String>): List<Any> {
        var campName = map["campName"]
        var trainingcamps = trainingCampService!!.getTrainingCamps(this.sessionCommunity.id, campName)
        return trainingcamps.intoMaps()
    }

    /**
     * 编辑或添加训练营信息
     */
    @GetMapping("/trainingcamps")
    fun addTrainingcamps(@RequestParam(required = false) id: Int?
                         , model: Model
                         , @ModelAttribute("trainingCamp") trainingCamp: TrainingCamp): String {
        var trainingCamp: TrainingCamp
        if (id != null && id > 0) {
            trainingCamp = trainingCampService!!.getTrainingCamp(id)
        } else {
            trainingCamp = TrainingCamp()
        }
        model.addAttribute("trainingCamp", trainingCamp)
        return "sec/community/thirdactivity/trainingcamps"
    }

    /**
     * 保存训练营信息
     */
    @PostMapping("/saveTrainingCamps")
    fun saveTrainingCamps(@ModelAttribute("trainingCamp") trainingCamp: TrainingCamp
                          , model: Model
                          , redirectAttributes: RedirectAttributes): String {
        if (trainingCamp.id != null && trainingCamp.id > 0) {

            trainingCamp.modifiedBy = this.sessionUser!!.id
            trainingCamp.communityId = this.sessionCommunity!!.id
            trainingCamp.modified = DateUtil.date().toTimestamp()
            trainingCampService!!.update(trainingCamp)
        } else {
            if(trainingCampService!!.getTrainingCampName(trainingCamp.campName)!=null){
                model.addAttribute("errorMessage", "训练营【${trainingCamp.campName}】已存在！")
                return "/sec/community/thirdactivity/trainingcamps"
            }
            trainingCamp.modifiedBy = this.sessionUser!!.id
            trainingCamp.communityId = this.sessionCommunity!!.id
            trainingCamp.modified = DateUtil.date().toTimestamp()
            trainingCampService!!.insert(trainingCamp)
        }
        redirectAttributes.addFlashAttribute("globalMessage", "操作成功！")
        return "redirect:/sec/community/thirdactivity/trainingcamp"
    }

    /**
     * 删除训练营
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteTrainingCamp/{id}", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun deleteTrainingCamp(@PathVariable id: Int): String {
        trainingCampService!!.deleteTrainingCamp(id)
        return "redirect:/sec/community/thirdactivity/trainingcamp"
    }

}