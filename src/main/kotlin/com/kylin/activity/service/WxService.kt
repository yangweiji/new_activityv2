package com.kylin.activity.service

import cn.binarywang.wx.miniapp.api.WxMaService
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult
import com.github.binarywang.wxpay.exception.WxPayException
import com.github.binarywang.wxpay.service.WxPayService
import com.kylin.activity.util.LogUtil
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

    /**
     * 申请退款处理
     */
    @Throws(WxPayException::class)
    fun refund(@RequestBody request: WxPayRefundRequest): WxPayRefundResult {
        var result = WxPayRefundResult()
        try {
            result = payService!!.refund(request)
            if (result.resultCode == "SUCCESS") {
                LogUtil.printLog("REFUND_SOURCE_UNSETTLED_FUNDS, refund ok, outTradeNo: ${request.outTradeNo}, outRefundNo: ${request.outRefundNo}")
            }
        }
        catch (e: WxPayException) {
            LogUtil.printLog("err_code: ${e.errCode}, err_code_des: ${e.errCodeDes}, outTradeNo: ${request.outTradeNo}, outRefundNo: ${request.outRefundNo}")
            if (e.resultCode == "FAIL" && e.errCode == "NOTENOUGH") {
                //尝试使用可用余额退款
                request.refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS"
                result = payService!!.refund(request)
                //SUCCESS/FAIL
                //SUCCESS退款申请接收成功，结果通过退款查询接口查询
                //FAIL 提交业务失败
                if (result.resultCode == "SUCCESS") {
                    LogUtil.printLog("REFUND_SOURCE_RECHARGE_FUNDS, refund ok, outTradeNo: ${request.outTradeNo}, outRefundNo: ${request.outRefundNo}")
                }
            }
        }
        finally {
        }
        return result
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