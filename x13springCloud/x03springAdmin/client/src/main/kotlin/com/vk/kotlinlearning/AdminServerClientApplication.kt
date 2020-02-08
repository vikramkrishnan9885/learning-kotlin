package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdminServerClientApplication

fun main(args: Array<String>) {
	runApplication<AdminServerClientApplication>(*args)
}
