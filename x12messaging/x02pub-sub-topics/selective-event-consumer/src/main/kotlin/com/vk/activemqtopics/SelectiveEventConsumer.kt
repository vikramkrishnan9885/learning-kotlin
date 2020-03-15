package com.vk.activemqtopics

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.concurrent.TimeUnit
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Session


class SelectiveEventConsumer {
    private val connectionUri = "tcp://localhost:61616"
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var session: Session? = null
    private var destination: Destination? = null
    private var selector: String? = null

    @Throws(Exception::class)
    fun before() {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory!!.createConnection()
        connection?.start()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session!!.createTopic("EVENTS.QUOTES")

        // THIS IS DIFFERENT
        // selector = System.getProperty("QuoteSel", "symbol = 'GOOG'")
        // selector = System.getProperty("QuoteSel","symbol='MSFT' AND price >= 800")
        selector = System.getProperty("QuoteSel", "symbol='GOOG' OR symbol='AAPL'")
    }

    @Throws(Exception::class)
    fun run() {
        println(" Running example with selector: $selector")
        // NOTE THE ADDITIONAL SELECTOR
        val consumer = session!!.createConsumer(destination, selector)
        consumer.messageListener = EventListener()
        TimeUnit.MINUTES.sleep(5)
        connection?.stop()
        consumer.close()
    }

    @Throws(java.lang.Exception::class)
    fun after() {
        if (connection != null) {
            connection!!.close()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val producer = SelectiveEventConsumer()
            print("\n\n\n")
            println("Starting example Selective Stock Ticker Consumer now...")
            try {
                producer.before()
                producer.run()
                producer.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Selective Stock Ticker Consumer app.")
            print("\n\n\n")
        }
    }
}