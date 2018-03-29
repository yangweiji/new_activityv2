package com.kylin.activity

import org.jooq.util.DefaultGeneratorStrategy
import org.jooq.util.Definition
import org.jooq.util.GeneratorStrategy

class CustomGeneratorStrategy : DefaultGeneratorStrategy() {
    override fun getJavaMemberName(definition: Definition, mode: GeneratorStrategy.Mode?): String {
        val name = super.getJavaMemberName(definition, mode)
        println(name)
        return name

    }
}