package com.vk.kotlinlearning.DBUser

import com.vk.kotlinlearning.commons.HibernateUtil.sessionFactory
import org.hibernate.Session
import java.util.*


fun main(args:Array<String>){
    println("Maven + Hibernate + MySQL")

    val session: Session = sessionFactory.openSession()
    session.beginTransaction()

    val userId = 100
    val username = "superman"
    val createdBy = "system"
    val createdAt = Date()
    val user = DBUser(userId, username, createdBy, createdAt)

    session.save(user)
    session.getTransaction().commit()
}