package com.vk.activemqjobs

import java.util.concurrent.TimeUnit
import javax.jms.Message
import javax.jms.MessageListener
import kotlin.random.Random

//
// THIS REALLY IS THE MEAT AND 2VEG OF THIS CONSUMER
//
// The MessageListener object in JMS allows us to receive and process messages asynchronously,
// freeing up our application to do other work if it needs to while messages are processed in
// a separate thread.
//
// A:B A implements B
class JobQueueListener : MessageListener {

    private val randomJobDelay:Random =  Random

    override fun onMessage(message: Message) {
        try {
            val jobId: Int? = message.getIntProperty("JobID")
            println("Worker is processing job:"+ jobId)
            println("Message is "+ message.toString())
            // SECONDS BETWEEN PROCESSING
            TimeUnit.MILLISECONDS.sleep((randomJobDelay.nextLong(10)))
        } catch (e: Exception){
            println("Whoops!")
        }
    }

}