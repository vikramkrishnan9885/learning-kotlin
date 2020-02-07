package com.vk.kotlinlearning

// ADDED THIS

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloWorldController {
    @GetMapping("/hello-worlds/{name}")
    fun getHelloWorld(@PathVariable name: String): String {
        return "Hello World $name"
    }
}