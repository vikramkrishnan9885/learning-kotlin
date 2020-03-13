package com.vk.activemqtesting

import org.apache.activemq.ActiveMQConnectionFactory
import java.lang.Exception
import javax.jms.*

class Main () {
    var connectionUri: String = "tcp://localhost:61616"
    var connection:Connection? = null
    var session:Session? = null
    var destination: Destination? = null


    // BAR - before, after, run
    @Throws(Exception::class)
    fun before(queueName: String): Unit {
        val connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory.createConnection()
        connection!!.start()
        session = connection!!.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session!!.createQueue(queueName)
    }

    @Throws(Exception::class)
    fun after(): Unit {
        if(connection != null) {
            connection!!.close()
        }
    }

    @Throws(Exception::class)
    fun run(msg:String): Unit {
        val messageProducer: MessageProducer = session!!.createProducer(destination)
        try {
            val textMessage : TextMessage = session!!.createTextMessage()
            textMessage.text = msg
            messageProducer.send(textMessage)
        } finally {
            messageProducer.close()
        }

        val messageConsumer: MessageConsumer = session!!.createConsumer(destination)
        try {
            val textMessage: TextMessage =  messageConsumer.receive() as TextMessage
            println(textMessage.text)
        } finally {
            messageConsumer.close()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val example: Main = Main()
            println("Starting SimpleJMS example now ...")
            try {
                example.before("TestQueue")
                example.run("This is the message we want to send")
                example.after()
            } catch (e: Exception) {
                println(e)
            }

            println("Finished running the SimpleJMS example.")
        }
    }
}