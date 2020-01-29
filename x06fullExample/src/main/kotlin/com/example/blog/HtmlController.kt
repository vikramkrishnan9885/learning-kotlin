package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

// Classic controllers can be annotated with the @Controller annotation.
// This is simply a specialization of the @Component class and allows implementation classes
// to be autodetected through the classpath scanning.
//
// @Controller is typically used in combination with a @RequestMapping annotation used on
// request handling methods.
@Controller
class HtmlController(private val repository: ArticleRepository) {

    @GetMapping("/")
    fun blog(model: Model): String {
        // Here we import the org.springframework.ui.set extension function in
        // order to be able to write model["title"] = "Blog" instead of
        // model.addAttribute("title", "Blog").
        // https://docs.spring.io/spring-framework/docs/current/kdoc-api/spring-framework/
        model["title"] = "Blog"
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() } // see fun below
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = repository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    fun Article.render() = RenderedArticle(
            slug,
            title,
            headline,
            content,
            author,
            addedAt.format()
    )

    // Note no id. Also data class with vals
    data class RenderedArticle(
            val slug: String,
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val addedAt: String
    )
}