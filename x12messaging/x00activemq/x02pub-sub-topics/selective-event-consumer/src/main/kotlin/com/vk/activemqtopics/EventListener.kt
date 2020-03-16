package com.vk.activemqtopics

import javax.jms.Message
import javax.jms.MessageListener

// THIS IS IDENTICAL TO CONSUMER
class EventListener : MessageListener {
    override fun onMessage(message: Message) {
        try {
            val price = message.getFloatProperty("price")
            val symbol = message.getStringProperty("symbol")
            println("Price Update: $symbol[$$price]")
        } catch (e: Exception) {
            println("Worker caught an Exception")
        }
    }
}