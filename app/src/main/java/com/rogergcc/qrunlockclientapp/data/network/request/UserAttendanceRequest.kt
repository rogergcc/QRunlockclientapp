package com.rogergcc.qrunlockclientapp.data.network.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAttendanceRequest(
    @SerializedName("chat_id") val chatId: String,
    @SerializedName("event_id") val eventId: Int
) : Parcelable