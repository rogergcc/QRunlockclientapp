package com.rogergcc.qrunlockclientapp.data.network.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class Attendance(
    @SerializedName("chat_id") val chatId: String? = "",
    @SerializedName("created_at") val createdAt: String? = "",
    @SerializedName("event_id") val eventId: Int? = 0,
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("updated_at") val updatedAt: String? = ""
) : Parcelable