package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfigServerClientApplication

fun main(args: Array<String>) {
	runApplication<ConfigServerClientApplication>(*args)
}
