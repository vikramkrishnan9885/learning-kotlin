package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MigrationsApplication

fun main(args: Array<String>) {
	runApplication<MigrationsApplication>(*args)
}
