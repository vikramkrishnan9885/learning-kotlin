package com.vk.kotlinlearning.querying

import com.vk.kotlinlearning.commons.HibernateUtil
import org.hibernate.Query
import org.hibernate.Session

fun main(args:Array<String>) {

    val session: Session = HibernateUtil.sessionFactory.openSession()

    println("====================================================================================")
    println("NAMED QUERY TESTING")
    println("====================================================================================")

    var queryXml: Query = session.getNamedQuery("findStockByStockCode").setString("stockCode", "4715")
    println("NAMED QUERY XML NOT NATIVE SQL: " )
    println(queryXml.list())

    println("------------------------------------------------------------------------------------")
    var queryXmlSql: Query = session.getNamedQuery("findStockByStockCodeNativeSQL").setString("stockCode", "8914")
    println("NAMED QUERY XML NATIVE SQL: " )
    println(queryXmlSql.list())

    //println("------------------------------------------------------------------------------------")
    //var queryAnnotated: Query = session.getNamedQuery("findStockByStockId").setInteger("stockId", 4)
    //println("NAMED QUERY ANNOTATED NOT NATIVE SQL: " )
    //println(queryAnnotated.list())

    /**println("------------------------------------------------------------------------------------")
    var queryAnnotatedSql: Query = session.getNamedQuery("Stock.findStockByStockNameNativeSQL").setString("stockName", "GENX")
    println("NAMED QUERY ANNOTATED NATIVE SQL: " )
    println(queryAnnotatedSql.list())
    */
}