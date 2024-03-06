package com.rogergcc.qrunlockclientapp.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class RegisterAttendanceResponse(
    @SerializedName("data") val attendance: Attendance? = Attendance(),
    @SerializedName("error") val error: String? = "",
    @SerializedName("message") val message: String? = "",
    @SerializedName("status") val status: Int? = 0
) : Parcelable
