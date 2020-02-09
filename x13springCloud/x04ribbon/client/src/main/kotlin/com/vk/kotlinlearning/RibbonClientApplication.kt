package com.vk.kotlinlearning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.ribbon.RibbonClient

@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name="my-server", configuration = arrayOf(RibbonConfiguration::class))
class RibbonClientApplication

fun main(args: Array<String>) {
	runApplication<RibbonClientApplication>(*args)
}
