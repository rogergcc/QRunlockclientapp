package com.rogergcc.qrunlockclientapp.data


import com.rogergcc.qrunlockclientapp.data.network.QrEventClient
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.domain.model.toDomain
import javax.inject.Inject

class TheAttendanceRepository @Inject constructor(
    private val apiClient: QrEventClient
) {
    suspend fun getAttendanceData(): List<AttendanceDomain> {
        val response = apiClient.verifyRegisterUserAttendance()
        response.toString()
        return response.map { it.toDomain() }
    }


}