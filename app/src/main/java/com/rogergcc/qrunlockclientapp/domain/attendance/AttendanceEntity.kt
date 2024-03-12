package com.rogergcc.qrunlockclientapp.domain.attendance

import android.os.Parcelable
import com.rogergcc.qrunlockclientapp.data.model.AttedancesRows


import kotlinx.parcelize.Parcelize

@Parcelize
data class AttendanceEntity (
        val id: Int?,
        val chatId: String?,
        val eventId: Int?,
        val createdAt: String?,
        val updatedAt: String?
        ):Parcelable

fun AttedancesRows.toDomain() = AttendanceEntity(
        id,
        chatId,
        eventId,
        createdAt,
        updatedAt
        )
