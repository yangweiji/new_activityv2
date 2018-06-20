package com.kylin.activity.controller.pub

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse
import com.kylin.activity.databases.tables.pojos.ActivityUser
import com.kylin.activity.databases.tables.pojos.PayOrder
import com.kylin.activity.service.OrderService
import com.kylin.activity.service.PayService
import com.xiaoleilu.hutool.date.DateUtil
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 小程序付款相关api
 */
@RestController
@RequestMapping("pub/wx/pay")
class WxPayController {
    /**
     * 付款相关操作服务
     */
    @Autowired
    private var payService: PayService? = null

    @Autowired
    private var orderService: OrderService? = null

    @RequestMapping("/create")
    @Transactional
    fun createOrder(request: HttpServletRequest, @RequestBody order: PayOrder): Any {
        return payService!!.createOrder(request, order)
    }

    @RequestMapping("/check/{id}")
    fun checkOrder(@PathVariable id:Int):PayOrder {
        var order = orderService!!.getOrder(id)
        return order
    }


    @RequestMapping("/notify")
    @Transactional
    fun notify(request:HttpServletRequest,  response: HttpServletResponse): Any {
        return payService!!.notify(request, response)
    }

}