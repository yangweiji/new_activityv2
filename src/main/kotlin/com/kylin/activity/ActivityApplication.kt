package com.kylin.activity

import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.EnableTransactionManagement
import sun.security.util.SecurityConstants

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
class ActivityApplication

fun main(args: Array<String>) {
    SpringApplication.run(ActivityApplication::class.java, *args)
}

//@Bean
//fun servletContainerFactory(): EmbeddedServletContainerFactory {
//    val tomcat = object : TomcatEmbeddedServletContainerFactory() {
//        override fun postProcessContext(context: Context?) {
//            val securityConstraint = SecurityConstraint()
//            securityConstraint.userConstraint = "CONFIDENTIAL"
//            val collection = SecurityCollection()
//            collection.addPattern("/*")
//            securityConstraint.addCollection(collection)
//            context!!.addConstraint(securityConstraint)
//        }
//    }
//
//    tomcat.addAdditionalTomcatConnectors(httpConnector())
//    return tomcat
//}
//
//@Bean
//fun httpConnector(): Connector {
//    var connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
//    connector.scheme = "http"
//    connector.port = 8080
//    connector.secure = false
//    connector.redirectPort = 8078
//    return connector
//}