package com.vk.kotlinlearning

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties("limits-service")
data class Configuration (
        var minimum: Int? = null,
        var maximum: Int? = null
)