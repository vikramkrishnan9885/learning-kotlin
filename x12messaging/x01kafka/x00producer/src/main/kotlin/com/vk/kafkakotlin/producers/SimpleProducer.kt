package com.vk.kafkakotlin.producers

import com.github.javafaker.Faker
import com.vk.kafkakotlin.models.Person
import com.vk.kafkakotlin.models.personsTopic
import com.vk.kafkakotlin.utils.jsonMapper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import java.util.logging.Logger

class SimpleProducer(brokers: String) {

    private val logger = Logger.getLogger("SimpleProducer")
    private val producer = createProducer(brokers)

    private fun createProducer(brokers: String): Producer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java

        return KafkaProducer<String, String>(props)
    }

    fun produce(){
        val faker = Faker()
        while (true) {
            val fakePerson =  Person(
                firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                birthDate = faker.date().birthday()
            )
            logger.info("Generated a person $fakePerson")

            val fakePersonJson = jsonMapper.writeValueAsString(fakePerson)
            logger.info("JSON data: $fakePersonJson")

            val futureResult = producer.send(ProducerRecord(personsTopic, fakePersonJson))
            logger.info("Sent a record")

        }
    }

}