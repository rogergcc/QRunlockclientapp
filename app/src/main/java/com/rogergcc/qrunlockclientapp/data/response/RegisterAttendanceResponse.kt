package com.rogergcc.qrunlockclientapp.data.response


import com.google.gson.annotations.SerializedName

data class RegisterAttendanceResponse(
    @SerializedName("data") val attendance: Attendance? = Attendance(),
    @SerializedName("error") val error: String? = "",
    @SerializedName("message") val message: String? = "",
    @SerializedName("status") val status: Int? = 0
)
