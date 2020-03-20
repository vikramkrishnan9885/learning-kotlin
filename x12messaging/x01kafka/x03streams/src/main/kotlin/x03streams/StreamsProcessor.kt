package x03streams

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.apache.log4j.LogManager
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.*

class StreamsProcessor(val brokers: String) {

    private val logger = LogManager.getLogger(javaClass)

    fun process() {
        val streamsBuilder = StreamsBuilder()

        val personJsonStream: KStream<String, String> =
            streamsBuilder.stream(
                personsTopic, Consumed.with(
                    Serdes.String(),
                    Serdes.String()
                )
            )

        val personStream: KStream<String, Person> = personJsonStream.mapValues { v ->
            val person = jsonMapper.readValue(v, Person::class.java)
            logger.debug("Person: $person")
            person
        }

        val resStream: KStream<String, String> = personStream.map { _, p ->
            val birthDateLocal = p.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val age = Period.between(birthDateLocal, LocalDate.now()).getYears()
            logger.debug("Age: $age")
            KeyValue("${p.firstName} ${p.lastName}", "$age")
        }

        resStream.to(agesTopic, Produced.with(Serdes.String(), Serdes.String()))

        val topology = streamsBuilder.build()

        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["application.id"] = "kafka-tutorial"
        val streams = KafkaStreams(topology, props)
        streams.start()
    }
}