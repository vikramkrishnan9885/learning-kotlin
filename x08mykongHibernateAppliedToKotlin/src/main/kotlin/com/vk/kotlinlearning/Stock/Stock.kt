package com.vk.kotlinlearning.Stock

import javax.persistence.Entity
import javax.persistence.NamedNativeQuery
import javax.persistence.Table

//https://www.reddit.com/r/Kotlin/comments/78artp/annotation_problems_in_kotlin/

//@NamedQueries(
//    NamedQuery(
//            name = "findStockByStockName",
//            query = "from Stock s where s.stockName = :stockName"
//    ),
//    NamedQuery(
//            name = "findStockByStockId",
//            query = "from Stock s where s.stockId = :stockId"
//    )
//)

//@NamedNativeQueries(
    //,
//    NamedNativeQuery(
//        name = "findStockByStockIdNativeSQL",
//        query = "select * from stock s where s.stock_id = :stockId",
//            resultClass = Stock::class
//    )
//)

@Entity
@Table(name = "STOCK")
@NamedNativeQuery(
        name = "Stock.findStockByStockNameNativeSQL",
        query = "select * from stock s where s.stock_name = :stockName",
        resultClass = Stock::class
)
data class Stock(
        var stockId: Int=0,
        var stockCode: String="",
        var stockName: String=""
)