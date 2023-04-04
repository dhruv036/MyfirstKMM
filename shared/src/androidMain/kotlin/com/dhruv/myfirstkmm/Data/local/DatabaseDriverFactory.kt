package com.dhruv.myfirstkmm.Data.local

import android.content.Context
import com.dhruv.NoteAppKMM.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver() : SqlDriver{
        return AndroidSqliteDriver(NoteDatabase.Schema,context,"note.db")
    }
}