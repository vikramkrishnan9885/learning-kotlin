package com.vk.kotlinlearning.DBUser

import java.util.*
import javax.persistence.*

@Entity
@Table(name="DBUSER")
data class DBUser(

        @Id
        @Column(name="USER_ID", unique = true, nullable = false, precision = 5, scale = 0)
        var userId: Int,

        @Column(name="USER_NAME", nullable = false, length = 20)
        var username: String,

        @Column(name = "CREATED_BY", nullable = false, length = 20)
        var createdBy: String,

        @Temporal(TemporalType.DATE)
        @Column(name = "CREATED_DATE", nullable = false, length = 7)
        var createdAt: Date
)