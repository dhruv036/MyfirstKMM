package com.dhruv.myfirstkmm.android.note_list

import com.dhruv.myfirstkmm.Domain.note.Note

data class NoteListState(
    val noteList : List<Note> = emptyList(),
    val searchText : String  ="",
    val isSearchFieldActive : Boolean =false
)