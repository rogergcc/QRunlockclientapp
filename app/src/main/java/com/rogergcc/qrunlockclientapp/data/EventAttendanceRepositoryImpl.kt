package com.rogergcc.qrunlockclientapp.data


import com.rogergcc.qrunlockclientapp.data.network.QrEventClient
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.attendance.AttendanceEntity
import com.rogergcc.qrunlockclientapp.domain.attendance.IEventAttendanceRepository
import com.rogergcc.qrunlockclientapp.domain.attendance.toDomain
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
import javax.inject.Inject

class EventAttendanceRepositoryImpl @Inject constructor(
    private val apiClient: QrEventClient
): IEventAttendanceRepository {
    override suspend fun getAttendanceData(): List<AttendanceEntity> {
        val response = apiClient.verifyRegisterUserAttendance()
        response.toString()
        return response.map { it.toDomain() }
    }

    override suspend fun registerAttendance(userCode: String): RegisterAttendanceResponse {
        try {
            return apiClient.registerAttendanceRecord(userCode)
        } catch (e: Exception) {
            TimberAppLogger.e("[EventAttendanceRepositoryImpl] (registerAttendance) ${e.message}")
            throw Exception(e.message)
        }
    }

}