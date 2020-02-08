package com.vk.kotlinlearning

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ClientBean {

    @Value("\${test.greeting}")
    // OTHERWISE IT GIVES INITIALIZATION ERROR
    private var msg1: String? = null

    @Value("\${test.msg}")
    private var msg2: String? = null

    //@Value("\${test.greeting}")
    //private var msg3: String? = null

    //@Value("\${test.msg}")
    //private var msg4: String? = null

    @PostConstruct
    fun postConstruct() {
        println("========================================================================")
        println(msg1)
        println("------------------------------------------------------------------------")
        println(msg2)
        //println("------------------------------------------------------------------------")
        //println(msg3)
        //println("------------------------------------------------------------------------")
        //println(msg4)
        println("========================================================================")

    }
}