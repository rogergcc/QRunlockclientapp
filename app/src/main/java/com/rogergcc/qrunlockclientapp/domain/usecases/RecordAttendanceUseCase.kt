package com.rogergcc.qrunlockclientapp.domain.usecases

import com.rogergcc.qrunlockclientapp.data.network.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.repository.IEventAttendanceRepository
import com.rogergcc.qrunlockclientapp.presentation.helper.TimberAppLogger
import javax.inject.Inject


/**
 * Created on marzo.
 * year 2024 .
 */
class RecordAttendanceUseCase @Inject constructor(
    private val repository: IEventAttendanceRepository
){

    suspend operator fun invoke(userCode: String): RegisterAttendanceResponse {
        try {
            return repository.registerAttendance(userCode)
        } catch (e: Exception) {
            TimberAppLogger.e("[RecordAttendanceUseCase] (invoke) ${e.message}")
            throw Exception(e.message)
        }
    }

}