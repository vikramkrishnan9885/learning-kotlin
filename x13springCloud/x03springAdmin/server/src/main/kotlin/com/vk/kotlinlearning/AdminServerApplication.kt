package com.vk.kotlinlearning

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

// https://codecentric.github.io/spring-boot-admin/2.1.6/
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@SpringBootApplication
class AdminServerApplication

fun main(args: Array<String>) {
	runApplication<AdminServerApplication>(*args)
}
