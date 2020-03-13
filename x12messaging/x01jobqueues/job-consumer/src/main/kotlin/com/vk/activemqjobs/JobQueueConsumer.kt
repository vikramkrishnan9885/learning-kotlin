package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.MessageConsumer
import javax.jms.Session

class JobQueueConsumer {

    private var connectionFactory: ActiveMQConnectionFactory? = null
    private val connectionUri:String = "tcp://localhost:61616"
    private var connection: Connection? = null
    private var destination: Destination? = null
    private var session: Session? = null

    @Throws(Exception::class)
    fun before(): Unit {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory?.createConnection()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session?.createQueue("JOBQ.Work")
    }

    @Throws(Exception::class)
    fun after(): Unit {
        if (connection != null) {
            connection?.close();
        }
    }

    @Throws(Exception::class)
    fun run(): Unit {
        val consumer: MessageConsumer? = session?.createConsumer(destination)
    }
}