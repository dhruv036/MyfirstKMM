package com.dhruv.myfirstkmm.Domain.note

import com.dhruv.myfirstkmm.CommonParcelable
import com.dhruv.myfirstkmm.CommonParcelize
import com.dhruv.myfirstkmm.CommonTypeParceler
import com.dhruv.myfirstkmm.LocalDateTimeParceler
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable
import com.dhruv.myfirstkmm.Presentation.*
import kotlinx.datetime.LocalDateTime


@CommonParcelize
@Serializable
data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val created: LocalDateTime,
): CommonParcelable {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun getRandomColor() = colors.random()
    }
}
