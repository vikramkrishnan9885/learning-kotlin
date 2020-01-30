package com.vk.kotlinlearning

import com.vk.kotlinlearning.services.GreetingService
import com.vk.kotlinlearning.services.GreetingServiceImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthApplication

fun main(args: Array<String>) {
	runApplication<HealthApplication>(*args)

	//val greetingService: GreetingService = GreetingServiceImpl()
	//println(greetingService.getGreeting())
}
