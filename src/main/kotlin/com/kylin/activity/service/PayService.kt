package com.kylin.activity.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest
import com.kylin.activity.databases.tables.daos.ActivityTicketDao
import com.kylin.activity.databases.tables.daos.PayOrderDao
import com.kylin.activity.databases.tables.daos.UserDao
import com.kylin.activity.databases.tables.pojos.ActivityUser
import com.kylin.activity.databases.tables.pojos.CommunityUser
import com.kylin.activity.databases.tables.pojos.PayOrder
import com.kylin.activity.util.KylinUtil
import com.xiaoleilu.hutool.date.DateUtil
import org.apache.commons.io.IOUtils
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class PayService {
    @Autowired
    private val wxService: WxService? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val payOrderDao: PayOrderDao? = null

    @Autowired
    private val ticketDao: ActivityTicketDao? = null

    @Autowired
    private val util: KylinUtil? = null


    @Autowired
    private val create: DSLContext? = null

    @Autowired
    private val env: Environment? = null

    @Autowired
    private val activityService: ActivityService? = null

    @Autowired
    private val userDao: UserDao? = null


    /**
     * 团体用户服务
     */
    @Autowired
    private val thirdUserService: ThirdUserService? = null


    /**
     * 创建付款订单
     */
    fun createOrder(request: HttpServletRequest, order: PayOrder): Any {

        val user = userService!!.getUser(order.userId)

        var start = DateUtil.date()
        if(order.activityTicketId != null && order.activityTicketId != 0) { // 活动票
            var ticket = ticketDao!!.fetchOneById(order.activityTicketId)
            order.activityId = ticket.activityId
//            order.price = ticket.price
        } else {
            order.price = BigDecimal(env!!.getProperty("vip.level1.price").toDouble())
        }
        order.created = start.toTimestamp()
        order.userId = user.id
        order.status = 1


        payOrderDao!!.insert(order)

        order.extenalId ="D${start.toString("yyyyMMddHHmmss")}" + String.format("%08d", order.id)

        payOrderDao!!.update(order)

        var unifiedOrder = WxPayUnifiedOrderRequest()
        unifiedOrder.body = order.body
        unifiedOrder.outTradeNo = order.extenalId
        unifiedOrder.totalFee = (order.price.toDouble() * 100).toInt()//元转成分
        unifiedOrder.openid = user.openId
        unifiedOrder.attach = order.id.toString()
        unifiedOrder.spbillCreateIp = util!!.getClientIpAddress(request)
        unifiedOrder.timeStart = start.toString("yyyyMMddHHmmss")
        unifiedOrder.timeExpire = DateUtil.offsetDay(start, 1).toString("yyyyMMddHHmmss")
        unifiedOrder.notifyUrl = util!!.getBaseUrl(request) + "/pub/wx/pay/notify/"
        unifiedOrder.tradeType = "JSAPI"
        var payInfo: Any =  wxService!!.payService!!.createOrder(unifiedOrder)
        return arrayOf(payInfo, order.id)
    }

    /**
     * 检查订单的状态，如果订单为未完成时，将从微信查询订单，并更新订单状态
     */
    fun checkOrder(id:Int):Int {
        synchronized(id) {
            var order = payOrderDao!!.fetchOneById(id)
            if (order.status != 2) {
                updateOrder(order)
            }
            return order.status
        }
    }

    /**
     * 从微信查询订单，并更新订单状态， 活动订单会生成报名记录， 会员订单会更新会员信息
     */
    fun updateOrder(order: PayOrder) {
        var wxOrder = wxService!!.payService!!.queryOrder(null, order.extenalId)
        if (wxOrder.returnCode == "SUCCESS" && wxOrder.tradeState == "SUCCESS") {
            if (order.status == 1) {
                order.status = 2
                order.payTime = DateUtil.parse(wxOrder.timeEnd, "yyyyMMddHHmmss").toTimestamp()
                payOrderDao!!.update(order)

                if (order.activityId != null && order.activityId > 0) { //活动报名
                    val mapper = jacksonObjectMapper()
                    var activityUser = mapper.readValue(order.otherInfo, ActivityUser::class.java)
                    activityService!!.saveAttend(activityUser)
                } else {
                    //升级VIP
                    var year = order.otherInfo.toInt()
                    thirdUserService!!.updateVipYear(order.communityId, order.userId, year)
                }
            }
        }

    }

    /**
     * 支付成功回调接口调用服务
     */
    fun notify(request:HttpServletRequest,  response: HttpServletResponse): Any {
        return try {
            val xmlResult = IOUtils.toString(request.inputStream, request.characterEncoding)
            val result = wxService!!.payService!!.parseOrderNotifyResult(xmlResult)
            // 结果正确
            val orderId = result.outTradeNo
            val tradeNo = result.transactionId
            var attach = result.attach
            val totalFee = util!!.feeToYuan(result.totalFee!!)

            var status = checkOrder(attach.toInt())

            if(status == 2){
                //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
                WxPayNotifyResponse.success("处理成功")
            } else {
                WxPayNotifyResponse.fail("订单状态未完成")
            }


        } catch (e:Exception){
            WxPayNotifyResponse.fail(e.message)
        }

    }
}