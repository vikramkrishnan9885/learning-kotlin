package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

// The @RestController annotation was introduced in Spring 4.0 to simplify the creation of RESTful web services.
// It's a convenience annotation that combines @Controller and @ResponseBody
// – which eliminates the need to annotate every request handling method of the controller class
// with the @ResponseBody annotation.
//
// The @Controller is a specialization of @Component annotation
// while @RestController is a specialization of @Controller annotation.
// It is actually a convenience controller annotated with @Controller and @ResponseBody as shown below.
//
// @Target(value=TYPE)
// @Retention(value=RUNTIME)
// @Documented
// @Controller
// @ResponseBody
// public @interface RestController
//
// and here is how the declaration of @Controller looks like:
//
// @Target(value=TYPE)
// @Retention(value=RUNTIME)
// @Documented
// @Component
// public @interface Controller
//
//Read more: https://javarevisited.blogspot.com/2017/08/difference-between-restcontroller-and-controller-annotations-spring-mvc-rest.html#ixzz6COBL0e1J
//
// One of the key difference between @Controler and @RestCotroller in Spring MVC
// is that once you mark a class @RestController then every method is written a
// domain object instead of a view.
//
@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {
    //
    // The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    //
    //There are companion annotations for other HTTP verbs (e.g. @PostMapping for POST).
    // There is also a @RequestMapping annotation that they all derive from, and can serve as a synonym
    // (e.g. @RequestMapping(method=GET))
    //
    @GetMapping("/")
    // WHEN YOU ENTER THE URL IN YOUR BROWSER DON'T FORGET THE TRAILING SLASH
    //
    // The controller is annotated with the @RestController annotation, therefore the @ResponseBody isn't required.
    // Every request handling method of the controller class automatically serializes
    // return objects into HttpResponse.
    //
    fun findAll() = repository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) =
            repository.findBySlug(slug) ?: ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
            repository.findByLogin(login) ?: ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}