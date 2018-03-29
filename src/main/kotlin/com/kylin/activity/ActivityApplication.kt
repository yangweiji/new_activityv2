package com.kylin.activity

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class ActivityApplication

fun main(args: Array<String>) {
    SpringApplication.run(ActivityApplication::class.java, *args)
}
