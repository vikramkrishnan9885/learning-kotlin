package com.vk.kafkakotlin.models

import java.util.*

// Note that val is compulsory unlike scala
data class Person (
        val firstName: String,
        val lastName: String,
        val birthDate: Date
)

val personsTopic = "persons"
