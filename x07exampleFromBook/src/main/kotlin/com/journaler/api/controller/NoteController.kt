package com.journaler.api.controller

import com.journaler.api.data.Note
import com.journaler.api.data.NoteDTO
import com.journaler.api.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

// For Notes:
// [ GET ] /notes, to obtain a list of notes
// [ PUT ] /notes, to insert a new Note
// [ DELETE ] /notes/{id}, to remove an existing Note with ID
// [ POST ] /notes, to update an existing Note

// The class itself has several very important annotations applied:
//
//  * @RestController is an annotation that is itself annotated with @Controller and @ResponseBody.
//      The difference between a standard MVC controller (@Controller) and @RestController is in
//      how the HTTP response body is created.
//      @Controller is used with View technology (usually returning HTML as a result).
//      @RestController returns an instance of a class that is serialized in an HTTP response as JSON or XML.
//
//  * @RequestMapping("/notes") is an annotation with which we map all requests, starting with "notes/",
//      to this class. We can map the whole path of an API call or map just methods, as in our case.
//
//  * @GetMapping(value = "/obtain",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)),
//      we map GET HTTP method to "obtain" path. The result we expect is JSON. So the getNotes() method
//      will be triggered each time the client triggers the GET method for the URL http://host/notes/obtain.
//      We will, for now, just return a simple string.
//

@RestController
@RequestMapping("/notes")
class NoteController {

    // With @Autowired, we tell Spring Framework that a particular field must be dependency injected.
    // The field must be defined as late init var.
    @Autowired
    private lateinit var service: NoteService

    // Equivalent to:
    // @RequestMapping(
    //               method = arrayOf(RequestMethod.GET),
    //               value = "/obtain",
    //               produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    //   ) fun getNotes(): String ...
    //
    // The following parameters can be passed to @RequestMapping annotation:
    //      * name: Assigning a name to mapping.
    //      * value: Mapping we assign. This parameter can also accept multiple mappings, for example:
    //                value={"", "/something", "something*", "something/*,
    //                **/something"}
    //      * path: Only in the Servlet environment, the path mapping URIs
    //      * method: HTTP method to map GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, and TRACE
    //      * params: Parameters of the mapping
    //      * headers: Headers of the mapping
    //      * consumes: Consumable media types of the mapping produces: Producible media types of the mapping
    @GetMapping(
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    //fun getNotes(): List<Note> {
    //    return listOf(
    //            Note("My first note", "Message for my first note"),
    //            Note("VK's second note", "Msg for second note")
    //    )
    //}
    fun getNotes() = service.getNotes()

    @PutMapping(
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun insertNode(
            @RequestBody note:NoteDTO
            //@RequestBody note: Note
    ):NoteDTO//: Note
    {
        val savedNote = service.insertNote(note)
        return savedNote
    }
    /**fun insertNote(
            @RequestBody note: Note
    ): Note {
        note.id = UUID.randomUUID().toString()
        println(note)
        return note
    }
    */

    @DeleteMapping(
            value = arrayOf("/{id}"),
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun deleteNote(@PathVariable(name = "id") id: String): Boolean {
        service.deleteNote(id)
        return true
    }
    /**fun deleteNote(@PathVariable(name = "id") id: String): Boolean {
        println("Removing: $id")
        return true
    }
    */

    @PostMapping(
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    ) //fun updateNote(@RequestBody note: Note): Note {
    fun updateNote(@RequestBody note: NoteDTO): NoteDTO {
        val updatedNote = service.updateNote(note)
        return updatedNote
    }
    /**
    fun updateNote(@RequestBody note: Note): Note {
        note.title += " [ updated ]"
        note.message += " [ updated ]"
        return note
    }
    */
}