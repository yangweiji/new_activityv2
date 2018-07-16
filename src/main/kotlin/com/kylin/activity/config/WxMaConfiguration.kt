package com.kylin.activity.config

import cn.binarywang.wx.miniapp.api.WxMaService
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl
import cn.binarywang.wx.miniapp.config.WxMaConfig
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author [Binary Wang](https://github.com/binarywang)
 */
@Configuration
@ConditionalOnClass(WxMaService::class)
@EnableConfigurationProperties(WxMaProperties::class)
class WxMaConfiguration {

//    private val logHandler = { wxMessage:WxMaMessage, context, service, sessionManager ->
//        println("收到消息：" + wxMessage.toString())
//        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
//                .toUser(wxMessage.getFromUser()).build())
//    }
//
//    private val textHandler = { wxMessage, context, service, sessionManager ->
//        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
//                .toUser(wxMessage.getFromUser()).build())
//    }
//
//    private val picHandler = { wxMessage, context, service, sessionManager ->
//        try {
//            val uploadResult = service.getMediaService()
//                    .uploadMedia("image", "png",
//                            ClassLoader.getSystemResourceAsStream("tmp.png"))
//            service.getMsgService().sendKefuMsg(
//                    WxMaKefuMessage
//                            .newImageBuilder()
//                            .mediaId(uploadResult.getMediaId())
//                            .toUser(wxMessage.getFromUser())
//                            .build())
//        } catch (e: WxErrorException) {
//            e.printStackTrace()
//        }
//    }
//
//    private val qrcodeHandler = { wxMessage, context, service, sessionManager ->
//        try {
//            val file = service.getQrcodeService().createQrcode("123", 430)
//            val uploadResult = service.getMediaService().uploadMedia("image", file)
//            service.getMsgService().sendKefuMsg(
//                    WxMaKefuMessage
//                            .newImageBuilder()
//                            .mediaId(uploadResult.getMediaId())
//                            .toUser(wxMessage.getFromUser())
//                            .build())
//        } catch (e: WxErrorException) {
//            e.printStackTrace()
//        }
//    }
    @Autowired
    private val properties: WxMaProperties? = null

    @Bean
    @ConditionalOnMissingBean
    fun maConfig(): WxMaConfig {
        val config = WxMaInMemoryConfig()
        config.appid = this.properties!!.appid
        config.secret = this.properties.secret
        config.token = this.properties.token
        config.aesKey = this.properties.aesKey
        config.msgDataFormat = this.properties.msgDataFormat

        return config
    }

    @Bean
    @ConditionalOnMissingBean
    fun wxMaService(maConfig: WxMaConfig): WxMaService {
        val service = WxMaServiceImpl()
        service.wxMaConfig = maConfig
        return service
    }

//    @Bean
//    fun router(service: WxMaService): WxMaMessageRouter {
//        val router = WxMaMessageRouter(service)
//        router
//                .rule().handler(logHandler).next()
//                .rule().async(false).content("模板").handler(templateMsgHandler).end()
//                .rule().async(false).content("文本").handler(textHandler).end()
//                .rule().async(false).content("图片").handler(picHandler).end()
//                .rule().async(false).content("二维码").handler(qrcodeHandler).end()
//        return router
//    }
//
//    companion object {
//        private val templateMsgHandler = { wxMessage, context, service, sessionManager ->
//            service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
//                    .templateId("此处更换为自己的模板id")
//                    .formId("自己替换可用的formid")
//                    .data(Lists.newArrayList<WxMaTemplateMessage.Data>(
//                            WxMaTemplateMessage.Data("keyword1", "339208499", "#173177")))
//                    .toUser(wxMessage.getFromUser())
//                    .build())
//        }
//    }

}