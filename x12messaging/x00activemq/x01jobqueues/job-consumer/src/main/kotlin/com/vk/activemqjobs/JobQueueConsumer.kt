package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import java.util.concurrent.TimeUnit
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.MessageConsumer
import javax.jms.Session


class JobQueueConsumer {
    //
    // When working with JMS queues and ActiveMQ, it's good to understand the idea of message prefetching.
    // ActiveMQ will send a set number of messages to a new consumer when it subscribes to a destination
    // if that destination has pending messages. The number of messages sent is known as the consumer's
    // prefetch limit; the default prefetch limit for a queue, for instance, is 1,000 messages.
    //
    // Why is this prefetch limit thing important? For a single consumer on a destination, prefetching
    // the messages can speed up message processing since there will always be messages waiting on the
    // client end of the Connection object as opposed to having to wait after every message acknowledgement
    // for the broker to send across another message. When there's more than one consumer subscribed to a
    // destination, that's when things can get interesting.
    //
    // Let's say, for instance, you wanted to have two consumers processing jobs from a queue and there are
    // 500 messages (jobs) on the queue. Depending on the time interval between consumer 1 subscribing
    // to the queue and consumer 2 subscribing, ActiveMQ could have sent all 500 Messages to the first consumer
    // since its default prefetch size is 1,000. This is not ideal as consumer 2 now sits idle and
    // consumer 1 must process all 500 jobs on its own, so your consumers won't do any load balancing.
    // It's important when you are designing your queue processing applications then to tune the prefetch value
    // for your consumers in order to get the behavior and performance characteristics desired.
    //
    // In our sample job consumer application, we actually did do this; were you paying attention?
    // Take another look at the Connection URI used in the sample
    //
    // The addition to the prefetch policy setting for queues of 1 means that ActiveMQ will
    // only send one message to our application and won't send another until the onMessage call of our MessageListener
    // returns and the message is acknowledged. You can observe the effects that changing the prefetch will have by
    // changing the value used in the application to something like 500 or 1,000.
    //
    // http://activemq.apache.org/what-is-the-prefetch-limit-for.html
    //
    private val connectionUri:String = "tcp://localhost:61616?jms.prefetchPolicy.queuePrefetch=100"

    // IDENTICAL TO PRODUCER
    private var connectionFactory: ActiveMQConnectionFactory? = null
    private var connection: Connection? = null
    private var destination: Destination? = null
    private var session: Session? = null

    // IDENTICAL TO PRODUCER
    @Throws(Exception::class)
    fun before(): Unit {
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        connection = connectionFactory?.createConnection()

        // THIS LINE IS DIFFERENT AND MISSING THIS MEANS NOTHING WILL HAPPEN AS NO
        // CONNECTIONS ARE STARTED
        // https://activemq.apache.org/i-am-not-receiving-any-messages-what-is-wrong
        connection?.start()

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
        consumer?.messageListener = JobQueueListener()
        // THIS INDICATES AFTER HOW MUCH TIME WILL THE CONSUMER SLEEP AND CONNECTION GET DISCONNECTED
        TimeUnit.SECONDS.sleep(100)
        connection?.stop()
        consumer?.close()
        //
        // You might be wondering why we didn't just use the synchronous receive methods that were used
        // in the SimpleJMS application we looked at in the previous recipe.
        // val messageConsumer: MessageConsumer = session!!.createConsumer(destination)
        //        try {
        //            val textMessage: TextMessage =  messageConsumer.receive() as TextMessage
        //            println(textMessage.text)
        //        } finally {
        //            messageConsumer.close()
        //        }
        // We very well could have written the application to call the MessageConsumer
        // object's synchronous receive call, there's just a few points to keep in mind when you choose
        // that route. When you use the synchronous receive call, the thread is blocked waiting on a message,
        // it can't do any other work. Also when you go the synchronous route, you need to provide some
        // means of breaking the thread out of the receive call either via timeout or by having some other
        // thread close to the MessageConsumer or Connection object that owns it.
        // Using the asynchronous style gives you more flexibility in your application design.
        //
        // In a typical job processing application the number of jobs to process is not set.
        // It's also common to have more than one queue that holds jobs to be processed.
        // Because messages sent to a Queue are evenly dispatched to the MessageConsumer objects
        // that have subscribed to it, we can easily deal with increasing numbers of messages by
        // adding more consumer applications to process them if the current consumers become overloaded.
        //
    }

    //NOTE THAT THIS IS A MIRROR IMAGE OF PRODUCER
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val consumer = JobQueueConsumer()
            print("\n\n\n")
            println("Starting example Job Queue Consumer now...")
            try {
                consumer.before()
                consumer.run()
                consumer.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Job Queue Consumer app.")
            print("\n\n\n")
        }
    }
}