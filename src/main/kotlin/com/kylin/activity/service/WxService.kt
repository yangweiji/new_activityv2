package com.kylin.activity.service

import cn.binarywang.wx.miniapp.api.WxMaService
import com.github.binarywang.wxpay.service.WxPayService
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.github.binarywang.wxpay.exception.WxPayException
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import org.springframework.web.bind.annotation.RequestBody
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult

@Service
class WxService {
    @Autowired
    val maService: WxMaService? = null

    @Autowired
    val payService: WxPayService? = null


    @Throws(WxPayException::class)
    fun refund(@RequestBody request: WxPayRefundRequest): WxPayRefundResult {
        return payService!!.refund(request)
    }

}