package com.rogergcc.qrunlockclientapp.data.network.response
import com.google.gson.annotations.SerializedName


data class AttendanceResponse(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("body")
    val body: Body?
)

data class Body(
    @SerializedName("findRows")
    val attedancesRows: List<AttedancesRows>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("affectedRows")
    val affectedRows: Boolean?
)

data class AttedancesRows(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("chat_id")
    val chatId: String?,
    @SerializedName("event_id")
    val eventId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

//{
//    "status": 404,
//    "message": "Not found",
//    "body": {
//    "findRows": [],
//    "message": "Not found",
//    "affectedRows": false
//}
//}