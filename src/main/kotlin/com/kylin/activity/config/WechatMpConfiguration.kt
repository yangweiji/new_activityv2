package com.kylin.activity.config

import me.chanjar.weixin.mp.api.WxMpConfigStorage
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage
import me.chanjar.weixin.mp.api.WxMpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@ConditionalOnClass(WxMpService::class)
@EnableConfigurationProperties(WechatMpProperties::class)
class WechatMpConfiguration {

    @Autowired
    private val properties: WechatMpProperties? = null


    @Bean
    @ConditionalOnMissingBean
    fun configStorage(): WxMpConfigStorage {
        val configStorage = WxMpInMemoryConfigStorage()
        configStorage.appId = properties!!.appId
        configStorage.secret = properties!!.secret
        configStorage.token = properties!!.token
        configStorage.aesKey = properties!!.aesKey
        return configStorage
    }

    @Bean
    @ConditionalOnMissingBean
    fun wxMpService(configStorage: WxMpConfigStorage): WxMpService {
        val wxMpService = me.chanjar.weixin.mp.api.impl.WxMpServiceImpl()
        wxMpService.wxMpConfigStorage = configStorage
        return wxMpService
    }


}
