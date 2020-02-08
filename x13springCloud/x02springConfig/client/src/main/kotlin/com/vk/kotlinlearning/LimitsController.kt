package com.vk.kotlinlearning

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * THIS DID NOT WORK
@RefreshScope
@RestController
class LimitsController {

    @GetMapping("/limits")
    fun getLimits(): Limits {
        return Limits()
    }
}


data class Limits(
        @Value("\${limit-service.minimum:Config Server is not working. Please check...}")
        private var minimum: Int?=null,
        @Value("\${limit-service.maximum:Config Server is not working. Please check...}")
        private var maximum: Int?=null

)
 */

@RefreshScope
@RestController
class LimitsController {

    @Value("\${limits-service.minimum:0}")
    private var minimum: String?=null

    @GetMapping("/limits")
    fun getLimits(): String {
        return this.minimum.toString()
    }
}