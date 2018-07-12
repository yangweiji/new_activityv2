package com.kylin.activity.service

import cn.binarywang.wx.miniapp.api.WxMaService
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult
import com.github.binarywang.wxpay.exception.WxPayException
import com.github.binarywang.wxpay.service.WxPayService
import me.chanjar.weixin.mp.api.WxMpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.io.File

@Service
class WxService {
    @Autowired
    val maService: WxMaService? = null

    @Autowired
    val payService: WxPayService? = null

    @Autowired
    val mpService: WxMpService? = null

    @Throws(WxPayException::class)
    fun refund(@RequestBody request: WxPayRefundRequest): WxPayRefundResult {
        return payService!!.refund(request)
    }


    /**
     * 取得二维码
     * @param scene: 场景值
     * @param page: 小程序页面
     * @return 二维码文件
     */
    fun getQrCode(scene: String, page: String): File {
        return maService!!.qrcodeService!!.createWxaCodeUnlimit(scene, page)
    }
}