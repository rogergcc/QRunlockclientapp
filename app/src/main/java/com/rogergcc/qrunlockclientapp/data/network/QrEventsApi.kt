package com.rogergcc.qrunlockclientapp.data.network

import com.rogergcc.qrunlockclientapp.data.model.AttendanceResponse
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QrEventsApi {
    @GET("attendance")
    suspend fun getVerifyUserAttendance(): Response<AttendanceResponse>

    @POST("attendance")
    suspend fun registerAttendanceRecord(@Body request: UserAttendanceRequest): Response<RegisterAttendanceResponse?>

}