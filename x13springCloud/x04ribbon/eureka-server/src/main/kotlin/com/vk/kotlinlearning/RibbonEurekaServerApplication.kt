package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class RibbonEurekaServerApplication

fun main(args: Array<String>) {
	runApplication<RibbonEurekaServerApplication>(*args)
}
