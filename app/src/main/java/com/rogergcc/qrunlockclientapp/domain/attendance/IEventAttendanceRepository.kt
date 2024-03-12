package com.rogergcc.qrunlockclientapp.domain.attendance

import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse


interface IEventAttendanceRepository {

    suspend fun getAttendanceData(): List<AttendanceEntity>

    suspend fun registerAttendance(userCode: String): RegisterAttendanceResponse
}
