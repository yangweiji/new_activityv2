package com.kylin.activity

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.util.UrlPathHelper


@Configuration
class MvcConfig : WebMvcConfigurerAdapter() {

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        registry!!.addViewController("/login").setViewName("login")

    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
    }

    /**
     * 开启Maxtrix Variable功能
     */
    @Override
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        var urlPathHelper = UrlPathHelper()
        urlPathHelper.setRemoveSemicolonContent(false)
        configurer.urlPathHelper = urlPathHelper
    }
}
