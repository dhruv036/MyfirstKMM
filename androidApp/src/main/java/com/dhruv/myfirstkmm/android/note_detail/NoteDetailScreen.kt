package com.dhruv.myfirstkmm.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons as Icons

@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteId: Long? = null,
    viewModel: NoteDetailViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved){
        if (hasNoteBeenSaved){
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(
                onClick = viewModel::onSaveNote ,
                backgroundColor = Color.Black)
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.value.noteColor))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = state.value.noteTitle,
                hint = "Enter a Title...",
                isHintVisible = state.value.isNoteTitleHintVisible,
                onValueChange = viewModel::onNoteTitleChanged,
                onFocusChanged = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier =  Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.value.noteContent,
                hint = "Enter some content...",
                isHintVisible = state.value.isNoteContentHintVisible ,
                onValueChange = viewModel::onNoteContentChanged,
                onFocusChanged = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1F)
            )
        }
    }
}