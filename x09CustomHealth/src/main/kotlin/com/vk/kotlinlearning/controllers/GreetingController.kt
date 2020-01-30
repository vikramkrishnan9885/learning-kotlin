package com.vk.kotlinlearning.controllers

import com.vk.kotlinlearning.services.GreetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    @Autowired
    lateinit var greetingService: GreetingService

    @GetMapping("/hello",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))//produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    fun msg():String {
        return greetingService.getGreeting()
    }
}