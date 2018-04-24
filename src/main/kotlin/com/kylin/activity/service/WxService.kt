package com.kylin.activity.service

import com.github.binarywang.wxpay.service.WxPayService
import me.chanjar.weixin.common.bean.WxJsapiSignature
import org.springframework.stereotype.Service
import me.chanjar.weixin.mp.api.WxMpService
import org.springframework.beans.factory.annotation.Autowired
import com.github.binarywang.wxpay.exception.WxPayException
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import org.springframework.web.bind.annotation.RequestBody
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult





@Service
class WxService {
    @Autowired
    val mpService: WxMpService? = null

    @Autowired
    val payService: WxPayService? = null



    fun getJsApiInfo(url:String):WxJsapiSignature{
        var signature: WxJsapiSignature = mpService!!.createJsapiSignature(url)
        return signature
    }

    @Throws(WxPayException::class)
    fun refund(@RequestBody request: WxPayRefundRequest): WxPayRefundResult {
        return payService!!.refund(request)
    }



}