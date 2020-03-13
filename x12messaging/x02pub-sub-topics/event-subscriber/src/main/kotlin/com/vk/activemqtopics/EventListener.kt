package com.vk.activemqtopics

import javax.jms.Message
import javax.jms.MessageListener


class EventListener : MessageListener {
    override fun onMessage(message: Message) {
        try {
            // since our stock pricing data is stored in the message's properties, we don't need a message body here
            val price: Float? = message.getFloatProperty("price")
            val symbol: String? = message.getStringProperty("symbol")
            //println("Price Update: " + symbol+ "["+price.toString()+"]")
            println("Price Update: $symbol[$$price]")
        } catch (e: Exception) {
            println("Worker caught an Exception")
        }
    }
}