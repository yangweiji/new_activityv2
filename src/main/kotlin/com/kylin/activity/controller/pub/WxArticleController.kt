package com.kylin.activity.controller.pub

import com.kylin.activity.databases.tables.Activity
import com.kylin.activity.service.ArticleService
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.KylinUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 微信活动相关控制器
 * @author Richard C. Hu 2018-05-27
 */
@RestController
@RequestMapping("pub/wx/article")
class WxArticleController {

    /**
     * 文章服务
     */
    @Autowired
    private val articleService: ArticleService? = null

    /**
     * 通用服务
     */
    @Autowired
    private val commonService: CommonService? = null

    /**
     * 第三方工具
     */
    @Autowired
    private val util: KylinUtil? = null

    /**
     * 查询指定文章分类的文章信息
     * @param clientId: 团体组织标识
     * @param category: 文章分类
     * @return 文章信息集合
     */
    @GetMapping("/getArticleListByCategory")
    fun getArticleListByCategory(@RequestParam(required = false) clientId: Int?, @RequestParam(required = false) category: Int?): Any {
        //获取文章信息
        var items = articleService!!.getArticleListByCategory(category)
        //文章信息
        var mapList = mutableListOf<MutableMap<String, Any?>>()
        for (item in items.sortDesc("publish_time")) {
            var map = mutableMapOf<String, Any?>()
            var avatar: String? = null
            if (item["avatar"] != null) {
                avatar = commonService!!.getDownloadUrl(item.get("avatar", String::class.java), "middle")
            }
            map["id"] = item.get("id", Int::class.java)
            map["title"] = item.get("title").toString()
            map["summary"] = item.get("summary").toString()
            map["avatar"] = avatar
            map["publish_time"] = util!!.fromNow(item.get("publish_time"))

            mapList.add(map)
        }

        return mapList
    }

    /**
     * 显示文章详情内容
     * @param articleId: 活动ID
     * @return 单个活动信息
     */
    @GetMapping("/getArticleExt")
    fun getArticleExt(@RequestParam(required = false) articleId: Int?): Any {
        //文章详情信息
        var item = articleService!!.getArticleExt(articleId)
        var avatar: String?
        if (item!!["avatar"] != null) {
            avatar = commonService!!.getDownloadUrl(item.get("avatar", String::class.java))
            item.setValue(Activity.ACTIVITY.AVATAR, avatar)
        }

        var map = item.intoMap()
        if (map["publish_time"] != null) {
            map["publish_time"] = util!!.fromNow(item.get("publish_time"))
        }

        return map
    }
}