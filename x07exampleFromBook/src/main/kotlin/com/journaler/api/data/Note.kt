package com.journaler.api.data

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

// To use a class as a database entity, use @Entity annotation.
// To assign a table name to be used, use @Table annotation, as in our Note class example.
@Entity
@Table(name = "note")
data class Note(
        var title: String,
        var message: String,

        // @Id annotation is used to telling Spring what field our ID will be.
        // As you can see, we are using UUID2 as ID for our data.
        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        @Column(columnDefinition = "varchar(36)")
        var id:String="",
        //var id: String= UUID.randomUUID().toString(),

        var location: String=""
){
    /**
     * Hibernate tries creates a bean via reflection.
     * It does the object creation by calling the no-arg constructor.
     * Then it uses the setter methods to set the properties.
     *
     * If there is no default constructor, the following excpetion happens:
     * org.hibernate.InstantiationException: No default constructor for entity...
     */
    constructor() : this(
            "", "", "", ""
    )
}