package com.rogergcc.qrunlockclientapp.data


import com.rogergcc.qrunlockclientapp.data.network.QrEventClient
import com.rogergcc.qrunlockclientapp.data.network.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceEntity
import com.rogergcc.qrunlockclientapp.domain.repository.IEventAttendanceRepository
import com.rogergcc.qrunlockclientapp.domain.model.toDomain
import com.rogergcc.qrunlockclientapp.presentation.helper.TimberAppLogger
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