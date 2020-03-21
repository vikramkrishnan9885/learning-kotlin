package com.vk.activemq

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.concurrent.TimeUnit
import javax.jms.*


class Responder : MessageListener {
    private val connectionUri = "tcp://localhost:61616"
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var session: Session? = null
    private var destination: Destination? = null

    private var requestListener: MessageConsumer? = null
    private var responder: MessageProducer? = null

    @Throws(Exception::class)
    fun before() {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory!!.createConnection()
        connection?.start()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session!!.createQueue("REQUEST.QUEUE")
        responder = session!!.createProducer(null)
        requestListener = session!!.createConsumer(destination)
        requestListener?.setMessageListener(this)
    }

    @Throws(Exception::class)
    fun run() {
        TimeUnit.MINUTES.sleep(5)
    }

    @Throws(java.lang.Exception::class)
    fun after() {
        if (connection != null) {
            connection!!.close()
        }
    }

    override fun onMessage(message: Message) {
        try {
            // The JMSReplyTo property for a JMS message was added just for this sort of messaging pattern.
            // The responder application doesn't have to know anything about preconfigured destinations
            // for sending responses, it just needs to query the message for its JMSReplyTo address.
            // This is yet another example of the benefits of the loose coupling that comes from using the JMS API.
            val replyTo: Destination = message.getJMSReplyTo()
            if (replyTo != null) {
                val textMessage = message as TextMessage
                println(textMessage.text)
                val response: Message = session!!.createTextMessage("Job Finished")
                // Did you notice that we also assign a correlation ID to each request?
                // Why do we do that? Since there could be multiple responses for requests we've sent,
                // we might want to match up each response to ensure all our work gets done.
                // For example, our application could have stored the correlation IDs in a map along
                // with the job data and matched up the responses.
                // Also, our application could have checked on a timeout if any outstanding requests hadn't
                // arrived and could have resubmitted the unfinished job, or logged a warning to the administrator.
                // The JMSCorrelationID field allows you to build this sort of book keeping into your
                // request/response applications easily.
                response.setJMSCorrelationID(message.getJMSCorrelationID())
                responder!!.send(replyTo, response)
            }
        } catch (e: Exception) {
            println("Encounted an error while responding: " + e.message)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val example = Responder()
            print("\n\n\n")
            println("Starting Responder example now...")
            try {
                example.before()
                example.run()
                example.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the Responder example.")
            print("\n\n\n")
        }
    }
}

// Every JMS request/response application we develop follows the basic pattern we saw in this recipe.
// Looking at the code, we start to see how JMS code can become a bit tedious with all the setup of connections,
// sessions, producers and consumers. Fortunately, there are ways to reduce the amount of work needed by using
// other software components that exist on top of the JMS API and provide simpler abstractions to problems such
// as the request/response pattern. One such solution is the Apache Camel project (http://camel.apache.org/).
// Apache Camel is what's known as an integration framework that implements well-known Enterprise Integration Patterns
// (EIPs) such as request/response.
//
//Not only do Camel as well as other EIP frameworks provide a simpler way of implementing patterns such as
// request/response, they also build in error handling and recovery mechanics that make the developer's life simpler.
// It's a good idea to explore an EIP framework when you need to implement the more complex messaging patterns.