package com.dhruv.myfirstkmm.Data.note

import com.dhruv.NoteAppKMM.database.NoteDatabase
import com.dhruv.myfirstkmm.Domain.note.Note
import com.dhruv.myfirstkmm.Domain.note.NoteDataSource
import com.dhruv.myfirstkmm.Domain.time.DateTimeUtil
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime

class SqlDelightDataSource(db: NoteDatabase) : NoteDataSource {
    private val queries = db.noteQueries
    override suspend fun insertNote(note: Note) {
            queries.insertNote(
                id = note.id,
                title = note.title,
                content = note.content,
                colorHex = note.colorHex,
                created = DateTimeUtil.toEpocMillis(note.created),
            )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getAllNoteByID(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}