package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.MessageConsumer
import javax.jms.Session

class JobQueueConsumer {

    // IDENTICAL TO PRODUCER
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private val connectionUri:String = "tcp://localhost:61616"
    private var connection: Connection? = null
    private var destination: Destination? = null
    private var session: Session? = null

    // IDENTICAL TO PRODUCER
    @Throws(Exception::class)
    fun before(): Unit {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory?.createConnection()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session?.createQueue("JOBQ.Work")
    }

    // IDENTICAL TO PRODUCER
    @Throws(Exception::class)
    fun after(): Unit {
        if (connection != null) {
            connection?.close();
        }
    }

    @Throws(Exception::class)
    fun run(): Unit {
        //NOTE THAT THIS LINE IS A MIRROR IMAGE OF PRODUCER
        val consumer: MessageConsumer? = session?.createConsumer(destination)

        // THIS BIT IS DIFFERENT
    }

    //NOTE THAT THIS IS A MIRROR IMAGE OF PRODUCER
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val consumer = JobQueueConsumer()
            print("\n\n\n")
            println("Starting example Job Queue Producer now...")
            try {
                consumer.before()
                consumer.run()
                consumer.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Job Queue Producer app.")
            print("\n\n\n")
        }
    }
}