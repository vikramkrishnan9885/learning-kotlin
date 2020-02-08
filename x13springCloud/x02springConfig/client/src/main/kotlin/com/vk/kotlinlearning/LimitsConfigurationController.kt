package com.vk.kotlinlearning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RefreshScope
class LimitsConfigurationController {

    @Autowired
    lateinit var configuration: Configuration

    @GetMapping("/limits2")
    fun retrieveLimitsFromConfiguration(): LimitConfiguration {
        return LimitConfiguration(configuration.minimum!! , configuration.maximum!!)
    }
}