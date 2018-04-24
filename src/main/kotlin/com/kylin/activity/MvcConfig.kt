package com.kylin.activity

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.util.UrlPathHelper
import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.http.converter.HttpMessageConverter


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

    /**
     * 配置消息转换器
     */
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>?) {
//        //定义一个转换消息的对象
//        val fastConverter = FastJsonHttpMessageConverter()
//        //添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
//        val fastJsonConfig = FastJsonConfig()
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
//        //在转换器中添加配置信息
//        fastConverter.fastJsonConfig = fastJsonConfig
//        //将转换器添加到converters中
//        converters!!.add(fastConverter)

        for (converter in converters!!) {
            LogUtil.printLog(converter.javaClass.name)
        }
    }

}
