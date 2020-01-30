package com.journaler.api.repository

import com.journaler.api.data.Note
import org.springframework.data.repository.CrudRepository


/**
 * String is the type for ID we use.
 */
interface NoteRepository : CrudRepository<Note, String> {

    fun findByTitle(title: String): Iterable<Note>

}

// CrudRepository brings us the following  functionalities:
// save(): Saving one entity
// saveAll(): Saving multiple entities
// findById(): Returns entity with ID
// existsById(): Does entity with ID exist
// findAll(): Returns all entities
// findAllById(): Returns all entities with ID
// count(): Returns the number of entities available
// deleteById(): Deletes entities with ID
// delete(): Deletes one entity
// deleteAll(): Deletes all entities received as argument
// deleteAll() (without arguments): Deletes all entities