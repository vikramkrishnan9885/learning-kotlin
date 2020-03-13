package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.*


class JobQueueProducer() {

    // The properties of the JMS queue make it an ideal means of processing jobs;
    // let's review those properties again in the context of a job processing application:
    //
    // Messages placed on a queue are persisted until they are consumed by MessageConsumer,
    // jobs aren't lost if no active consumer is online.
    //
    // Queued messages can have a time to live (TTL) set, which causes them to time out and be removed from the queue.
    // This is good if a queued job is time sensitive.
    //
    // Queued messages are delivered in the order they were placed on the queue if a single consumer is active,
    // this is good if the jobs are order dependent.

    // FUC DS
    // Factory
    // URL
    // Connection
    // Destination
    // Session

    private var connectionFactory: ActiveMQConnectionFactory? = null
    private val connectionUri:String = "tcp://localhost:61616"
    private var connection: Connection? = null
    private var destination: Destination? = null
    private var session: Session? = null


    // BAR
    // BEFORE - START FCSD
    @Throws(Exception::class)
    fun before(): Unit {
        // OTHER THAN THIS LINE EVERYTHING ELSE USES THE JMS API
        connectionFactory = ActiveMQConnectionFactory(connectionUri)
        // !! is not Null Safe
        // https://kotlinlang.org/docs/reference/null-safety.html
        connection = connectionFactory?.createConnection()
        /**
         * A session is a single-threaded context for producing and consuming messages. You use sessions to create the following:
         *    Message producers
         *    Message consumers
         *    Messages
         *    Queue browsers
         *    Temporary queues and topics (see Creating Temporary Destinations)
         *
         * Sessions serialize the execution of message listeners
         * A session provides a transactional context with which to group a set of sends and receives into an atomic unit of work.
         * Sessions implement the Session interface. After you create a Connection object, you use it to create a Session:
         *
         *    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         *
         * The first argument means that the session is not transacted;
         * the second means that the session automatically acknowledges messages when they have been received successfully.
         * To create a transacted session, use the following code:
         *
         *     Session session = connection.createSession(true, 0);
         *
         * Here, the first argument means that the session is transacted;
         * the second indicates that message acknowledgment is not specified for transacted sessions.
         * If the session is transacted, the second argument is ignored, so it is a good idea to specify 0 to make
         * the meaning of your code clear.
         *
         * https://docs.oracle.com/javaee/5/api/javax/jms/Session.html
         *
         */
        session = connection?.createSession(false, Session.AUTO_ACKNOWLEDGE)
        destination = session?.createQueue("JOBQ.Work")


    }

    // AFTER - CLOSES CONNECTION
    @Throws(Exception::class)
    fun after(): Unit {
        if (connection != null) {
            connection?.close();
        }
    }

    // RUN
    @Throws(Exception::class)
    fun run(): Unit {

        val producer: MessageProducer? = session?.createProducer(destination)
        // NOTE THE FOR LOOP
        for (i in 0..999) {
            val message: TextMessage? = session?.createTextMessage("Job number: $i")
            message?.setIntProperty("JobID", i)
            producer?.send(message)
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val producer = JobQueueProducer()
            print("\n\n\n")
            println("Starting example Job Queue Producer now...")
            try {
                producer.before()
                producer.run()
                producer.after()
            } catch (e: java.lang.Exception) {
                println("Caught an exception during the example: " + e.message)
            }
            println("Finished running the sample Job Queue Producer app.")
            print("\n\n\n")
        }
    }
}