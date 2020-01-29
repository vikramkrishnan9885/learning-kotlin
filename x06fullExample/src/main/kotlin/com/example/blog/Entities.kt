package com.example.blog

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

// DEFININING ENTITIES
// In order to define an entity, you must create a class that is annotated with the @Entity annotation.
// The @Entity annotation is a marker annotation, which is used to discover persistent entities.
// For example, if you wanted to create a book entity, you would annotate it as follows:
//
// @Entity
// public class Book {
//   ...
// }
//
//
// By default, this entity will be mapped to the Book table, as determined by the given class name.
// If you wanted to map this entity to another table (and, optionally, a specific schema)
// you could use the @Table annotation to do that.
// Here's how you would map the Book class to a BOOKS table:
//
// @Entity
// @Table(name="BOOKS")
// public class Book {
//    ...
// }
//
//
// If the BOOKS table was in the PUBLISHING schema, you could add the schema to the @Table annotation:
//
// @Table(name="BOOKS", schema="PUBLISHING")
//

// https://kotlinlang.org/docs/reference/classes.html#constructors
@Entity
class Article(
    // Here we don’t use data classes with val properties because JPA is not designed to work with
    // immutable classes or the methods generated automatically by data classes.
    // If you are using other Spring Data flavor, most of them are designed to support such constructs
    // so you should use classes like data class User(val login: String, …​)
    // when using Spring Data MongoDB, Spring Data JDBC, etc.
    var title: String,
    var headline: String,
    var content: String,
    // JPA defines four annotations for defining relationship between entities:
    //
    // @OneToOne
    // @OneToMany
    // @ManyToOne
    // @ManyToMany
    //
    @ManyToOne var author: User,
    // Notice that we are using here our String.toSlug() extension
    // to provide a default argument to the slug parameter of Article constructor.
    // Optional parameters with default values are defined at the last position in order
    // to make it possible to omit them when using positional arguments (Kotlin also supports named arguments).
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    // One of the requirements for a relational database table is that it must contain a primary key,
    // or a key that uniquely identifies a specific row in the database.
    // In JPA, we use the @Id annotation to designate a field to be the table's primary key.
    // The primary key is required to be a Java primitive type, a primitive wrapper,
    // such as Integer or Long, a String, a Date, a BigInteger, or a BigDecimal.
    // While Spring Data JPA makes it possible to use natural IDs
    // (it could have been the login property in User class) via Persistable,
    // it is not a good fit with Kotlin due to KT-6653,
    // that’s why it is recommended to always use entities with generated IDs in Kotlin.
    @Id @GeneratedValue var id: Long? = null
)

@Entity
class User(
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null
)