package com.kylin.activity.util.thymeleaf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ThymeleafConfig {

    @Bean
    fun customDialect(): CustomDialect {
        return CustomDialect()
    }
}