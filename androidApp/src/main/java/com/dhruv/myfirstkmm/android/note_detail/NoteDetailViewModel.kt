package com.dhruv.myfirstkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.myfirstkmm.Domain.note.Note
import com.dhruv.myfirstkmm.Domain.note.NoteDataSource
import com.dhruv.myfirstkmm.Domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle","")
    private val isNoteTitleFocus = savedStateHandle.getStateFlow("isNoteTitleFocus",false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent","")
    private val isNoteContentFocus = savedStateHandle.getStateFlow("isNoteContentFocus",false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor",Note.getRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocus,
        noteContent,
        isNoteContentFocus,
        noteColor
    ) { title,isTitleFocus,content,isContentFocus,color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocus,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocus,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L){
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let {
                    savedStateHandle["noteTitle"] = it.title
                    savedStateHandle["noteContent"] = it.content
                    savedStateHandle["noteColor"] = it.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text : String) {
        savedStateHandle["noteTitle"] = text
    }
    fun onNoteContentChanged(text : String) {
        savedStateHandle["noteContent"] = text
    }
    fun onNoteTitleFocusChanged(isFocused : Boolean) {
        savedStateHandle["isNoteTitleFocus"] = isFocused
    }
    fun onNoteContentFocusChanged(isFocused : Boolean) {
        savedStateHandle["isNoteContentFocus"] = isFocused
    }

    fun onSaveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    existingNoteId,
                    noteTitle.value ,
                    noteContent.value,
                    noteColor.value,
                    DateTimeUtil.now() 
                )
            )
            _hasNoteBeenSaved.value =true
        }


    }

}