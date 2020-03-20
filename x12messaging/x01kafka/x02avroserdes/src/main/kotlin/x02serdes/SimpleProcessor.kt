package x02serdes

import com.github.javafaker.Faker
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.avro.generic.GenericRecordBuilder
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.io.File
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class SimpleProcessor(brokers:String, schemaRegistryUrl:String) {

    private val logger = Logger.getLogger("SimpleProcessor")
    private val producer = createProducer(brokers, schemaRegistryUrl)
    private val consumer = createConsumer(brokers, schemaRegistryUrl)
    val personsAvroTopic = "persons-avro"

    private fun createConsumer(brokers:String, schemaRegistryUrl: String): Consumer<String, GenericRecord> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["group.id"] = "person-processor"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = KafkaAvroDeserializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        return KafkaConsumer<String, GenericRecord>(props)
    }

    private fun createProducer(brokers: String, schemaRegistryUrl: String): Producer<String, GenericRecord> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        return KafkaProducer<String, GenericRecord>(props)
    }

    fun run() {

        consumer.subscribe(listOf(personsAvroTopic))

        val faker = Faker()
        val schema: Schema? = Schema.Parser().parse(File("src/main/resources/person.avsc"))
        while(true) {

            // GENERATE A FAKE PERSON
            val fakePerson = Person(
                    firstName = faker.name().firstName(),
                    lastName = faker.name().lastName(),
                    birthDate = faker.date().birthday()
            )
            logger.info("Generated a person: $fakePerson")

            // CREATE A GENERIC RECORD USING THE AVRO SCHEMA
            val avroPerson = GenericRecordBuilder(schema).apply {
                set("firstName", fakePerson.firstName)
                set("lastName", fakePerson.lastName)
                set("birthDate", fakePerson.birthDate.time)
            }.build()

            // PRODUCER WRITES TO KAFKA
            val futureResult: Future<RecordMetadata>? = producer.send(ProducerRecord(personsAvroTopic, avroPerson))
            logger.info("Sent record ${futureResult}")

            logger.info("------------------------------------------------")

            // CONSUMER READS FROM KAFKA
            val records: ConsumerRecords<String, GenericRecord> = consumer.poll(Duration.ofMillis(100))
            logger.info("Received ${records.count()} records")
            records.iterator().forEach {
                val personAvro = it.value()
                val person = Person(
                        firstName = personAvro["firstName"].toString(),
                        lastName = personAvro["lastName"].toString(),
                        birthDate = Date(personAvro["birthDate"] as Long)
                )
                logger.info("Person: $person")
                val birthDateLocal = person.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val age = Period.between(birthDateLocal, LocalDate.now()).getYears()
                logger.info("Age: $age")
            }
            logger.info("==================================================")
        }
    }
}