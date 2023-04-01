package com.dhruv.myfirstkmm.Domain

import com.dhruv.myfirstkmm.Presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String?,
    val content: String?,
    val colorHex: Long?,
    val created: LocalDateTime
){
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun getRandomColor() = colors.random()
    }
}