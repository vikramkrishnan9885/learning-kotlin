package com.vk.activemq

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.jms.*

// While decoupling of messaging applications is a primary driver of the JMS specification,
// there are cases where an application needs to send a request and will not continue until
// it receives a response indicating its request was handled.
// This sort of messaging pattern is known as request/response messaging,
// and you can think of it as a sort of Remote Procedure Call (RPC) over JMS
// if that helps.
//
//Traditionally, this type of architecture has been implemented using TCP client
// and server applications that operate synchronously across a network connection.
// There are several problems that arise in this implementation,
// the biggest of which can be scaling. Because the TCP client and server
// are tightly coupled, it's difficult to add new clients to handle an
// increasing workload. Using messaging based architecture we can reduce or
// eliminate this scaling issue along with other issues of fault tolerance and so on.
// In the messaging paradigm, a requester sends a request message to a queue located
// on a remote broker and one or more responders can take this message,
// process it, and return a response message to the requester.


class Requester : MessageListener {
    private val connectionUri = "tcp://localhost:61616"
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var session: Session? = null
    private var destination: Destination? = null

    private val done = CountDownLatch(NUM_REQUESTS)

    @Throws(Exception::class)
    fun before() {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory!!.createConnection()
        connection?.start()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session!!.createQueue("REQUEST.QUEUE")
    }

    @Throws(java.lang.Exception::class)
    fun after() {
        if (connection != null) {
            connection!!.close()
        }
    }

    @Throws(Exception::class)
    fun run() {
        val responseQ = session!!.createTemporaryQueue()
        val requester = session!!.createProducer(destination)
        val responseListener = session!!.createConsumer(responseQ)
        responseListener.messageListener = this
        for (i in 0 until NUM_REQUESTS) {
            val request = session!!.createTextMessage("Job Request")
            request.jmsReplyTo = responseQ
            request.jmsCorrelationID = "request: $i"
            requester.send(request)
        }
        if (done.await(10, TimeUnit.MINUTES)) {
            println("Woohoo! Work's all done!")
        } else {
            println("Doh!! Work didn't get done.")
        }
    }

    override fun onMessage(message: Message) {
        try {
            val jmsCorrelation: String = message.getJMSCorrelationID()
            if (!jmsCorrelation.startsWith("request")) {
                println("Received an unexpected response: $jmsCorrelation")
            }
            val txtResponse = message as TextMessage
            println(txtResponse.text)
            done.countDown()
        } catch (ex: Exception) {
        }
    }

    companion object {
        private const val NUM_REQUESTS = 10

        @JvmStatic
        fun main(args: Array<String>) {
            val example = Requester()
            print("\n\n\n")
            println("Starting Requester example now...")
            try {
                example.before()
                example.run()
                example.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the Requester example.")
            print("\n\n\n")
        }
    }


}