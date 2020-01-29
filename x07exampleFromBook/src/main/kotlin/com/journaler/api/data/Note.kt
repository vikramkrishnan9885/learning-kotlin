package com.journaler.api.data

import java.util.*

data class Note(var title: String,
                var message: String,
                var id: String= UUID.randomUUID().toString(),
                var location: String="")