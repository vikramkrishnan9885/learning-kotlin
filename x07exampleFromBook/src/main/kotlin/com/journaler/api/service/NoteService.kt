package com.journaler.api.service

import com.journaler.api.data.Note
import com.journaler.api.data.NoteDTO
import com.journaler.api.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

// An @Service annotated class is Service, originally defined by domain- driven design. That is an operation offered as an interface that stands alone in the model, with no encapsulated state.
// Spring offers the following commonly used annotations:
//        @Component
//        @Controller
//        @Repository
//        @Service
//
// Let's explain the difference between them.
//      @Component is a generalization stereotype for any component managed by Spring Framework.
//
// The following specializations are available: @Repository, @Service, and @Controller.
//
// Each is specialized for a different use:
//
// @Repository annotation is a marker for any class that fulfills the role of a Data Access Object (DAO) of a repository.
// It also offers the automatic translation of exceptions.
//
// @Controller annotation has already been covered in the previous examples.
//
// @Service annotated class will have the purpose of connecting a controller with
// all operations to all operations related to data repository.
//
// We mark beans with @Service to indicate that it's holding the business logic.
// Currently, it doesn’t offer any additional functionality over @Component

@Service("Note service")
class NoteService {

    @Autowired
    lateinit var repository: NoteRepository


    // THIRD VERSION: USES DTO. CLEARLY SHOWS THAT SERVICES TIE DTOs TO DAOs
    fun getNotes(): Iterable<NoteDTO> = repository.findAll().map { it -> NoteDTO(it) }

    fun insertNote(note: NoteDTO) = NoteDTO(
            repository.save(
                    Note(
                            title = note.title,
                            message = note.message,
                            location = note.location
                    )
            )
    )

    fun deleteNote(id: String) = repository.deleteById(id)

    fun updateNote(noteDto: NoteDTO): NoteDTO {
        var note = repository.findById(noteDto.id).get()
        note.title = noteDto.title
        note.message = noteDto.message
        note.location = noteDto.location
        note.modified = Date()
        note = repository.save(note)
        return NoteDTO(note)
    }

    fun findByTitle(title: String): Iterable<NoteDTO> {
        return repository.findByTitle(title).map { it -> NoteDTO(it) }
    }

    /** SECOND VERSION: RETURNS DOMAIN OBJECT CREATED BY REPOSITORY
    fun getNotes(): Iterable<Note> = repository.findAll()
    fun insertNote(note: Note): Note = repository.save(note)
    fun deleteNote(id: String) = repository.deleteById(id)
    fun updateNote(note: Note): Note = repository.save(note)
    */
    /** ORIGINAL VERSION
    fun getNotes(): List<Note> = listOf(
            Note(
                    UUID.randomUUID().toString(),
                    "My first note",
                    "This is a message for the 1st note."
            ),
            Note(
                    UUID.randomUUID().toString(),
                    "My second note",
                    "This is a message for the 2nd note."
            )
    )

    fun insertNote(note: Note): Note {
        note.id = UUID.randomUUID().toString()
        return note
    }
    */
}