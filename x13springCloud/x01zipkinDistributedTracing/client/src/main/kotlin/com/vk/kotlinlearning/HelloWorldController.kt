package com.vk.kotlinlearning

// ADDED THIS

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger


@RestController
class HelloWorldController {

    private val LOG: Logger = Logger.getLogger(HelloWorldController::class.simpleName)

    @GetMapping("/hello-worlds/{name}")
    fun getHelloWorld(@PathVariable name: String): String {
        LOG.log(Level.INFO, "This is a sample log from the controller")
        return "Hello World $name"
    }
}