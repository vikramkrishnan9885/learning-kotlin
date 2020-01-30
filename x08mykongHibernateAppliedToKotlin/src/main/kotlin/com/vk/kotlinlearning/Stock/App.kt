package com.vk.kotlinlearning.Stock

import com.vk.kotlinlearning.Stock.HibernateUtil.sessionFactory
import org.hibernate.Session

fun main(args: Array<String>) {
    println("Maven + Hibernate + MySQL")
    val session: Session = sessionFactory.openSession()
    session.beginTransaction()
    val stock = Stock()
    stock.stockCode = "8911" // HAS TO BE UNIQUE - LOOK AT XML FOR CONSTRAINTS
    stock.stockName = "GEMU" // HAS TO BE UNIQUE - LOOK AT XML FOR CONSTRAINTS
    session.save(stock)
    session.transaction.commit()
}
