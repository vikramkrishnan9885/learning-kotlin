package com.vk.kafkakotlin.serdes

import com.vk.kafkakotlin.models.Person
import com.vk.kafkakotlin.utils.jsonMapper
import org.apache.kafka.common.serialization.Serializer

class PersonSerializer : Serializer<Person> {
    override fun serialize(topic: String, data: Person?): ByteArray? {
        if (data == null) return null
        return jsonMapper.writeValueAsBytes(data)
    }

    override fun close() {}
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
}

