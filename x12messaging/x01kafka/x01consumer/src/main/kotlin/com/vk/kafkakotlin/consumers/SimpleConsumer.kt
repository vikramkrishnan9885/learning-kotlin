package com.vk.kafkakotlin.consumers

import com.vk.kafkakotlin.models.Person
import com.vk.kafkakotlin.serdes.PersonDeserializer
import com.vk.kafkakotlin.utils.jsonMapper
import com.vk.kafkakotlin.utils.personsTopic
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*
import java.util.logging.Logger

class SimpleConsumer(brokers: String) {

    private val logger = Logger.getLogger("SimpleConsumer")
    private val consumer = createConsumer(brokers)

    //private fun createConsumer(brokers: String): Consumer<String, String> {
    private fun createConsumer(brokers: String): Consumer<String, Person> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        // THIS DIFFERS FROM PRODUCER
        // We also need to provide a group ID: this is to identify the consumer group that our consumer will join.
        // If multiple consumers are started in parallel - either through different processes
        // or through different threads - each consumer will be assigned a subset of the partitions of the topic.
        // E.g. since our topic was created with 4 partitions,
        // we could create up to 4 consumers so as to consume data in parallel.
        props["group.id"] = "person-processor"
        props["key.deserializer"] = StringDeserializer::class.java
        // props["value.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = PersonDeserializer::class.java
        //return KafkaConsumer<String, String>(props)
        return KafkaConsumer<String, Person>(props)
    }


    fun process() {
        // Once our consumer is created, we can subscribe to the source topic
        // This has the effect of requesting dynamic assignment of the partitions to our consumer,
        // and to effectively join the consumer group.
        consumer.subscribe(listOf(personsTopic))

        logger.info("Consuming and processing data")

        while (true) {
            // The duration passed in parameter to the poll() method is a timeout:
            // the consumer will wait at most 1 second before returning.
            // The moment the broker will return records to the client also depends on the value of fetch.min.bytes,
            // which defaults to 1, and which defines the minimum amount of data the broker should wait to be available for the client.
            // Another configuration property is fetch.max.bytes (default = 52428800 bytes),
            // which defines how much data can be returned at once.
            //
            //In our case, the broker:
            //  *    will return all the records that are available without exceeding the capacity of the buffer (50 MB)
            //  *    will return as soon as 1 byte of data is available
            //  *    while waiting at most 1 second.
            // This also means that, if no records are available, the broker will return an empty list of records.
            //
            // The consumer automatically commits the offsets for you during the next call to poll()
            // if enable.auto.commit is set to true, which is the default.
            // The whole batch of records will therefore be committed: if your application crashes after processing
            // a few messages but not all of the records of a batch, they will not be committed and
            // will be processed again by another consumer. This is called at least once processing.
            val records = consumer.poll(Duration.ofSeconds(1))
            logger.info("Received ${records.count()} records")

            records.iterator().forEach {
                //val personJson = it.value()
                //logger.info("JSON data: $personJson")

                //val person = jsonMapper.readValue(personJson, Person::class.java)
                //logger.info("Person: $person")

                val person = it.value()
                logger.info("Person: $person")

                val birthDateLocal = person.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val age = Period.between(birthDateLocal, LocalDate.now()).getYears()
                logger.info("Age: $age")
            }
        }
    }
}