package br.com.sky.xyinc.repository.converter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef


object JacksonExtension {
    private const val REFLECTION_CACHE_SIZE = 512

    val jacksonObjectMapper: ObjectMapper by lazy {
        val kotlinModule = KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()

        jacksonObjectMapper().registerModule(kotlinModule)
            .also { it.registerModule(SimpleModule()) }
            .also { it.registerModule(JavaTimeModule()) }
            .also { it.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) }
            .also { it.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true) }
            .also { it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }
            .also { it.setSerializationInclusion(JsonInclude.Include.NON_NULL) }
            .also { it.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE) }
            .also { it.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true) }
    }
}

fun <T> T.objectToJson(): String =
    JacksonExtension.jacksonObjectMapper.writeValueAsString(this)

fun <T> T.objectToJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.convertValue(this, JsonNode::class.java)

inline fun <reified T> JsonNode.jsonNodeToObject(): T =
    JacksonExtension.jacksonObjectMapper.treeToValue(this, jacksonTypeRef<T>())

inline fun <reified T> String.jsonToObject(): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, jacksonTypeRef<T>())
