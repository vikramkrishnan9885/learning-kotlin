package com.vk.serverless

import java.io.*
import com.fasterxml.jackson.module.kotlin.*

data class HandlerInput(val who: String)
data class HandlerOutput(val message: String)

// AWS Lambda supports passing an input object as a Stream,
// and also supports an output Stream for returning a result.
// Combined with the Input/Output Stream form of the handler function,
// we are using the Jackson library with a Kotlin extension function to support serialization
// and deserialization of Kotlin data class types.
// https://docs.aws.amazon.com/lambda/latest/dg/java-handler-io-type-stream.html

class MyLambdaHandler {
    val mapper = jacksonObjectMapper()

    fun handleRequest(input: InputStream, output: OutputStream): Unit {
        val inputObj = mapper.readValue<HandlerInput>(input)
        mapper.writeValue(output, HandlerOutput("Hello ${inputObj.who}"))
    }
}