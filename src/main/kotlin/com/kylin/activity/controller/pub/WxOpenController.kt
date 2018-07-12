package com.kylin.activity.controller.pub

import com.kylin.activity.service.UserService
import com.kylin.activity.wx.mp.WechatMpProperties
import com.kylin.activity.wx.open.WechatOpenProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by 9kylin on 2018-07-12.
 */
@Controller
@RequestMapping("pub/wx/open")
class WxOpenController {

    /**
     * 微信开放平台配置
     */
    @Autowired
    val wechatOpenProperties: WechatOpenProperties? = null

    /**
     * 微信公众平台配置
     */
    @Autowired
    val wechatMpProperties: WechatMpProperties? = null

    /**
     * 用户服务
     */
    @Autowired
    val userService: UserService? = null

    @GetMapping("/login")
    fun login(request: HttpServletRequest, response: HttpServletResponse) {
        val state = UUID.randomUUID().toString()
        //保存至session中
        request.session.setAttribute("WECHAT_STATE", state)

        response.sendRedirect("https://open.weixin.qq.com/connect/qrconnect?appid=${wechatMpProperties!!.appId}&redirect_uri=${URLEncoder.encode(wechatOpenProperties!!.webRedirectUri, "UTF-8")}&response_type=code&scope=snsapi_login&state=$state#wechat_redirect")
    }
}