package com.rogergcc.qrunlockclientapp.data.network

import com.rogergcc.qrunlockclientapp.data.model.AttendanceResponse
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.POST

interface QrEventsApi {
    //http://localhost:7002/api/v1/attendance/1091868140
    @GET("attendance")
    suspend fun getVerifyUserAttendance(): Response<AttendanceResponse>

    @POST("attendance")
    suspend fun registerAttendanceRecord(userCode: String): Response<RegisterAttendanceResponse>


}