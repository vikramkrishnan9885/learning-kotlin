package com.vk.kotlinlearning

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*


@Service
class StudentServiceDelegate {
    @Autowired
    var restTemplate: RestTemplate? = null

    @HystrixCommand(fallbackMethod = "callStudentServiceAndGetData_Fallback")
    fun callStudentServiceAndGetData(schoolname: String): String {
        println("Getting School details for $schoolname")
        val response = restTemplate!!
                .exchange<String>("http://localhost:8098/getStudentDetailsForSchool/{schoolname}"
                        , HttpMethod.GET
                        , null
                        , object : ParameterizedTypeReference<String?>() {}, schoolname).body
        println("Response Received as " + response + " -  " + Date())
        return "NORMAL FLOW !!! - School Name -  " + schoolname + " :::  " +
                " Student Details " + response + " -  " + Date()
    }

    // Interesting naming pattern
    private fun callStudentServiceAndGetData_Fallback(schoolname: String): String {
        println("Student Service is down!!! fallback route enabled...")
        return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
                " Service will be back shortly - " + Date()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}