package com.rogergcc.qrunlockclientapp.domain.repository

import com.rogergcc.qrunlockclientapp.data.network.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceEntity


interface IEventAttendanceRepository {

    suspend fun getAttendanceData(): List<AttendanceEntity>

    suspend fun registerAttendance(userCode: String): RegisterAttendanceResponse
}
