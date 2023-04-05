package com.dhruv.myfirstkmm.Data.note

import com.dhruv.myfirstkmm.Domain.note.Note
import com.dhruv.myfirstkmm.Domain.time.DateTimeUtil

class SearchNotes {
    fun execute(notes: List<Note>, query: String) :List<Note>{
        if (query.isBlank())
            return notes
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) || it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpocMillis(it.created)
        }
    }
}