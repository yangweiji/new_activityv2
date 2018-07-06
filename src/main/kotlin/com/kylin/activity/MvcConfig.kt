package com.kylin.activity

import com.kylin.activity.service.CommunityService
import com.kylin.activity.util.LogUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.util.UrlPathHelper


@Configuration
class MvcConfig : WebMvcConfigurerAdapter() {

    @Autowired
    private val communityService: CommunityService? = null

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

    /**
     * 编写一个类，继承WebMvcConfigurerAdapter抽象类，将其放入Spring容器中，
     * 然后重写addInterceptors()方法，并调用给的参数InterceptorRegistry.addInterceptor()
     * 把自己编写的那个拦截器（MyHandlerInterceptor()）作为参数加进去。
     * 解决首页团体切换在不同页面中不显示的问题
     */
    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(MyHandlerInterceptor(communityService))
    }

}
