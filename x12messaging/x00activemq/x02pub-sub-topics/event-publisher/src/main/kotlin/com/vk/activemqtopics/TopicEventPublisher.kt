package com.vk.activemqtopics

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.*
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Message
import javax.jms.Session


class TopicEventPublisher {

    private val connectionUri = "tcp://localhost:61616"
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var session: Session? = null
    private var destination: Destination? = null

    private val pricePicker: Random = Random()
    private val symbols = ArrayList<String>(3)

    @Throws(Exception::class)
    fun before() {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory?.createConnection()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session?.createTopic("EVENTS.QUOTES")
        symbols.add("AAPL")
        symbols.add("GOOG")
        symbols.add("MSFT")
        symbols.add("ORCL")
    }

    @Throws(Exception::class)
    fun after() {
        if (connection != null) {
            connection?.close()
        }
    }

    @Throws(Exception::class)
    fun run() {
        val producer = session?.createProducer(destination)
        for (i in 0..9999) {
            println("Producer sending price update($i)")
            for (symbol in symbols) {
                val message: Message? = session?.createMessage()
                message?.setStringProperty("symbol", symbol)
                message?.setFloatProperty("price", pricePicker.nextFloat() * 1000)
                println(symbol + ": $ (" + message?.getFloatProperty("price") + ")")
                producer?.send(message)
            }
            Thread.sleep(5)
        }
        producer?.close()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val producer = TopicEventPublisher()
            print("\n\n\n")
            println("Starting example Stock Ticker Publisher now...")
            try {
                producer.before()
                producer.run()
                producer.after()
            } catch (e: Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Stock Ticker Publisher app.")
            print("\n\n\n")
        }
    }
}