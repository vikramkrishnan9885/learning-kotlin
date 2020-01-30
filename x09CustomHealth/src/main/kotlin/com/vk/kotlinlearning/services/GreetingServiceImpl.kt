package com.vk.kotlinlearning.services

import org.springframework.stereotype.Service
import java.util.*

@Service
class GreetingServiceImpl: GreetingService {
    override fun getGreeting(): String {
        val msg = greetingMessages[randomIndexGenerator(greetingMessages.size)]
        return msg
    }

    fun randomIndexGenerator(maxInt:Int): Int = Random().nextInt(maxInt)

    companion object {
        private val greetingMessages = arrayOf("Hello","Hi","Hola","Namaste","Asalaam Waleiykum")
    }
}