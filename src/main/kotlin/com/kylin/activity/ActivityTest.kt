package com.kylin.activity

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
import com.aliyuncs.profile.DefaultProfile
import com.fasterxml.jackson.databind.ObjectMapper
import com.kylin.activity.util.CommonService
import com.kylin.activity.util.LogUtil


data class Po(
    var activity: String? = null,
    var tickets: String? = null
)

/**
 * Created by 9kylin on 2018-06-02.
 */
class ActivityTest

fun main(args: Array<String>) {
    println("Hello World!")

    var po = Po("Activity", "Tickets")
    val om = ObjectMapper()
    var s = om.writeValueAsString(po)
    println(s)


    var mobiles = "13910438997,"
    println(mobiles.split(',').size)
    mobiles = "13910438997"
    println(mobiles.split(',').size)
//
//    //产品名称:云通信短信API产品,开发者无需替换
//    val product = "Dysmsapi"
//    //产品域名,开发者无需替换
//    val domain = "dysmsapi.aliyuncs.com"
//    val accessKeyId = "LTAIlFPG8CjuB20V"
//    val accessKeySecret = "lvEv4wLKP8Pe609j0bw38j6Mo3oXyd"
//    //模板变量实际内容：<=20字符
//    var templateParam= "{\"title\":\"【惜缘户外】\",\"time\":\"2018-06-03\",\"address\":\"北京市怀柔区\",\"reason\":\"天气\"}"
//
//    //可自助调整超时时间
//    System.setProperty("sun.net.client.defaultConnectTimeout", "10000")
//    System.setProperty("sun.net.client.defaultReadTimeout", "10000")
//
//    //初始化acsClient,暂不支持region化
//    val profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret)
//    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain)
//    val acsClient = DefaultAcsClient(profile)
//
//    //组装请求对象-具体描述见控制台-文档部分内容
//    val request = SendSmsRequest()
//    ////必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
//    request.phoneNumbers = "13910438997"
//    //必填:短信签名-可在短信控制台中找到
//    request.signName = "九麒活动吧"
//    //必填:短信模板-可在短信控制台中找到
//    request.templateCode = "SMS_136391188"
//    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//    request.templateParam = templateParam
//
//    var response =  acsClient.getAcsResponse<SendSmsResponse>(request)
//
//    LogUtil.printLog("code: ${response.code} message: ${response.message}")
}