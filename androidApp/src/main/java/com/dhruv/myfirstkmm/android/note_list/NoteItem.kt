package com.dhruv.myfirstkmm.android.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.dhruv.myfirstkmm.Domain.note.Note
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruv.myfirstkmm.Domain.time.DateTimeUtil

@Composable
fun NoteItem(
    note : Note,
    backgroundColor : Color,
    onNoteClicked: ()-> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val dateTime =  remember(note.created) {
        DateTimeUtil.formateNoteDate(note.created)
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable { onNoteClicked() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete Note",
                modifier = Modifier
                    .clickable(MutableInteractionSource(),null) {
                        onDeleteClicked()
                    }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = note.content,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = dateTime,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.End)
        )






    }

}