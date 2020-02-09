package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import java.util.logging.Logger

@EnableDiscoveryClient
@SpringBootApplication
class EurekaClientApplication{
	private val LOG: Logger = Logger.getLogger(EurekaClientApplication::class.simpleName)
}

fun main(args: Array<String>) {
	runApplication<EurekaClientApplication>(*args)
}
