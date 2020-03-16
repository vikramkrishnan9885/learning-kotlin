package com.vk.activemqtopics

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.concurrent.TimeUnit
import javax.jms.*


class TopicEventSubscriber {

    private val connectionUri = "tcp://localhost:61616"
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var session: Session? = null
    private var destination: Topic? = null

    @Throws(Exception::class)
    fun before() {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory?.createConnection()

        // UNIQUE CLIENT ID NEEDED
        val uuid = java.util.UUID.randomUUID()
        connection?.setClientID(uuid.toString())

        connection?.start()
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)

        // Comparing this code to our previous queue producer code, you may have noticed that the only difference is
        // that we created a topic destination from our session in the before() method
        // as opposed to creating a queue destination.
        destination = session?.createTopic("EVENTS.QUOTES")
    }

    @Throws(Exception::class)
    fun after() {
        if (connection != null) {
            connection?.close()
        }
    }

    @Throws(Exception::class)
    fun run() {
        // publish-subscribe messaging. This domain differs from point-to-point messaging in two key ways.
        // First, the messages sent to a topic are only dispatched to consumers that were listening before
        // the message was sent. Second, every consumer listening on a topic will get a copy of the messages sent to a topic.
        // In the preceding figure we can see the typical JMS topic usage scenario. We have a single producer that's sending messages to a particular topic. There are two consumers that are interested in receiving messages sent to the topic and as our producer sends its messages the JMS provider dispatches a copy of the message to each consumer. If the producer were to send its messages to the topic and neither of our subscribers were currently registered, the JMS provider would simply discard those messages.
        //
        //The JMS topic is ideal when you want to send notifications to clients but it's not essential that
        // offline clients be able to consume that data when they come online again
        // If, however, we run the price update producer to completion and then start our consumer,
        // they would not receive any data since the JMS topic domain doesn't retain messages by default
        // when there are no consumers.
        // THIS HAPPENED TO US
        val consumer: MessageConsumer? = session?.createConsumer(destination)
        // While it's true that the default behavior of a JMS topic is to not retain any message that is sent to it
        // when there are no active consumers, there are cases where you might want to have a consumer go offline and,
        // when they come back online, receive any messages that were sent while they were offline.
        // The JMS specification provides for this by defining a type of topic subscription known as a durable subscription.
        //
        //To create a durable subscription your code must call the createDurableSubscriber() method of a Session
        // instance as well as give your connection a unique name that your application must reuse each time it runs.

        // UNCOMMENT THIS AND COMMENT LINE BELOW FOR DURABLE SUB
        //val consumer = session?.createDurableSubscriber(destination,"Durable Consumer")

        // https://myadventuresincoding.wordpress.com/2011/08/16/jms-how-to-setup-a-durablesubscriber-with-a-messagelistener-using-activemq/
        // Things to keep in mind when using durable subscriptions
        // There are several limitations that you should keep in mind when using durable subscriptions,
        // let's take a brief look at them.
        //
        // Each JMS connection that is used to create a durable subscription must be assigned a unique client ID in order
        // for the provider to identify the client each time its durable subscription is activated.
        // Additionally the subscription needs its own ID value as well.
        //
        // The client must create the durable subscription at least once before the JMS provider will begin storing
        // messages for that subscription.
        //
        // Only one JMS connection can be active at any given time for the same client ID and subscription ID meaning
        // no load balancing is possible for multiple consumers on the same logical subscription.
        //
        // These limitations make using durable subscription much more complicated than using a queue when message
        // durability is a must. ActiveMQ provides a mechanism to help mitigate the limitations of durable subscriptions
        // known as Virtual Destinations, which you should investigate any time you start to think that a durable
        // subscription sounds like it might fit your use case

        consumer?.messageListener= EventListener()
        TimeUnit.SECONDS.sleep(100)
        connection?.stop()
        consumer?.close()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val subsciber = TopicEventSubscriber()
            print("\n\n\n")
            println("Starting example Stock Ticker Subscriber now...")
            try {
                subsciber.before()
                subsciber.run()
                subsciber.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Stock Ticker Subscriber app.")
            print("\n\n\n")
        }
    }
}