package com.kylin.activity

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

import org.codehaus.jackson.JsonGenerator
import org.codehaus.jackson.JsonProcessingException
import org.codehaus.jackson.map.JsonSerializer
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.map.SerializerProvider
import org.codehaus.jackson.map.ser.CustomSerializerFactory

/**
 * Created by 9kylin on 2018-04-20.
 */
class CustomMessageObjectMapper: ObjectMapper() {

    fun CustomMessageObjectMapper() {
        val factory = CustomSerializerFactory()
        factory.addGenericMapping(Date::class.java!!, object : JsonSerializer<Date>() {
            @Throws(IOException::class, JsonProcessingException::class)
            override fun serialize(value: Date,
                                   jsonGenerator: JsonGenerator,
                                   provider: SerializerProvider) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                jsonGenerator.writeString(sdf.format(value))
            }
        })

        this.setSerializerFactory(factory)
    }
}