package com.kylin.activity.config

import com.github.binarywang.wxpay.config.WxPayConfig
import com.github.binarywang.wxpay.service.WxPayService
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Binary Wang
 */
@Configuration
@ConditionalOnClass(WxPayService::class)
@EnableConfigurationProperties(WxPayProperties::class)
class WxPayConfiguration {
    @Autowired
    private val properties: WxPayProperties? = null

    @Bean
    @ConditionalOnMissingBean
    fun config(): WxPayConfig {
        val payConfig = WxPayConfig()
        payConfig.appId = properties!!.appId
        payConfig.mchId = properties!!.mchId
        payConfig.mchKey = properties!!.mchKey
        payConfig.subAppId = StringUtils.trimToNull(properties!!.subAppId)
        payConfig.subMchId = StringUtils.trimToNull(properties!!.subMchId)
        payConfig.keyPath = properties!!.keyPath

        return payConfig
    }

    @Bean
            //@ConditionalOnMissingBean
    fun wxPayService(payConfig: WxPayConfig): WxPayService {
        val wxPayService = WxPayServiceImpl()
        wxPayService.config = payConfig
        return wxPayService
    }

}
