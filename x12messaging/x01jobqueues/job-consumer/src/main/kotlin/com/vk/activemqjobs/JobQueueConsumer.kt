package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Session

class JobQueueConsumer {

    var connectionFactory: ActiveMQConnectionFactory? = null
    val connectionUri:String = "tcp://localhost:61616"
    var connection: Connection? = null
    var destination: Destination? = null
    var session: Session? = null

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

    }
}