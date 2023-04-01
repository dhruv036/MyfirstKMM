package com.dhruv.myfirstkmm.Domain

import kotlinx.datetime.*

class DateTimeUtil {

    fun now() : LocalDateTime{
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpocMillis(dateTime: LocalDateTime):Long{
            return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun formateNoteDate(dateTime: LocalDateTime) :String{
        val month =dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercaseChar() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else "${dateTime.dayOfMonth}"
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val min = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(min)
        }
    }
}