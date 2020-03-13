package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Session

class JobQueueProducer() {

    // FUC DS
    // Factory
    // URL
    // Connection
    // Destination
    // Session

    var connectionFactory: ActiveMQConnectionFactory? = null
    val connectionUri:String = "tcp://localhost:61616"
    var connection: Connection? = null
    var destination: Destination? = null
    var session: Session? = null


    // BAR
    // BEFORE - START FCSD
    @Throws(Exception::class)
    fun before(): Unit {
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

    }
}