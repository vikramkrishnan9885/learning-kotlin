package com.vk.kotlinlearning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class SchoolServiceController {
    @Autowired
    lateinit var studentServiceDelegate: StudentServiceDelegate

    @RequestMapping(value = ["/getSchoolDetails/{schoolname}"], method = [RequestMethod.GET])
    fun getStudents(@PathVariable schoolname: String): String {
        println("Going to call student service to get data!")
        return studentServiceDelegate.callStudentServiceAndGetData(schoolname)
    }
}