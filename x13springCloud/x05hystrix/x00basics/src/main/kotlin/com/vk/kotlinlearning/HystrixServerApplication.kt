package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HystrixServerApplication

fun main(args: Array<String>) {
	runApplication<HystrixServerApplication>(*args)
}
