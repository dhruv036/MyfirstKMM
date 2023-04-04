package com.dhruv.myfirstkmm.android.note_list

import android.database.DatabaseUtils
import android.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.myfirstkmm.Data.note.SearchNote
import com.dhruv.myfirstkmm.Domain.note.Note
import com.dhruv.myfirstkmm.Domain.note.NoteDataSource
import com.dhruv.myfirstkmm.Domain.time.DateTimeUtil
import com.dhruv.myfirstkmm.Presentation.BabyBlueHex
import com.dhruv.myfirstkmm.Presentation.LightGreenHex
import com.dhruv.myfirstkmm.Presentation.RedOrangeHex
import com.dhruv.myfirstkmm.Presentation.RedPinkHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlin.random.Random

import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource : NoteDataSource,
    private val saveStateHandel : SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNote()


    init {
        viewModelScope.launch {
             (1..10).forEach {
                noteDataSource.insertNote(
                    Note(
                        id = it.toLong(),
                        title = "Note $it",
                        content = "My note is good $it",
                        colorHex = BabyBlueHex,
                        created = DateTimeUtil.now()
                    )
                )
             }
        }
    }

    private val notes = saveStateHandel.getStateFlow("notes", emptyList<Note>())
    private val searchText = saveStateHandel.getStateFlow("searchText","")
    private val isSearchActive = saveStateHandel.getStateFlow("isSearchActive",false)

    val state = combine(notes,searchText,isSearchActive){ notes, seachText, isSearchText ->
        NoteListState(
            noteList = searchNotes.execute(notes,seachText),
            searchText = seachText,
            isSearchFieldActive = isSearchActive.value
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NoteListState())

    fun loadNotes(){
        viewModelScope.launch {
            saveStateHandel["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSeachTextChange(text: String){
        saveStateHandel["searchText"] = text
    }

    fun onToggleSearch(){
        saveStateHandel["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value){
            saveStateHandel["searchText"] = ""
        }
    }

    fun onDeleteNoteById(id: Long){
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}