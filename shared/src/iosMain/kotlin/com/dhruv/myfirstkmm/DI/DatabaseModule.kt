package com.dhruv.myfirstkmm.DI

import com.dhruv.NoteAppKMM.database.NoteDatabase
import com.dhruv.myfirstkmm.Data.local.DatabaseDriverFactory
import com.dhruv.myfirstkmm.Data.note.SqlDelightNoteDataSource
import com.dhruv.myfirstkmm.Domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource : NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}