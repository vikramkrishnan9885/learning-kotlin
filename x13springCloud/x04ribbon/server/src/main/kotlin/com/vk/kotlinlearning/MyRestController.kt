package com.vk.kotlinlearning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping

class MyRestController {

    @Autowired
    lateinit var environment: Environment

    @GetMapping("/")
    fun health() : String {
        return "Am healthy!"
    }

    @GetMapping("/backend")
    fun backend(): String {

        val serverPort = environment.getProperty("local.server.port")
        return "Hello from Host:localhost:: Port:" + serverPort

    }

}