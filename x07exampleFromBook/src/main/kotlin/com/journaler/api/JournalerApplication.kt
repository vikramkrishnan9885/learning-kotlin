package com.journaler.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JournalerApplication

fun main(args: Array<String>) {
	runApplication<JournalerApplication>(*args)
}

