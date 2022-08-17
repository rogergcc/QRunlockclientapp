package com.rogergcc.qrunlockclientapp.data.network

import com.rogergcc.qrunlockclientapp.data.model.AttendanceResponse
import retrofit2.Response

import retrofit2.http.GET

interface QrEventsApi {
    @GET("attendance")
    suspend fun getVerifyUserAttendance(): Response<AttendanceResponse>

}