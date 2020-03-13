package com.vk.activemqjobs

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Session

class Main() {

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
        

    }

    // AFTER - CLOSES CONNECTION
    @Throws(Exception::class)
    fun after(): Unit {

    }

    // RUN
    @Throws(Exception::class)
    fun run(): Unit {

    }
}