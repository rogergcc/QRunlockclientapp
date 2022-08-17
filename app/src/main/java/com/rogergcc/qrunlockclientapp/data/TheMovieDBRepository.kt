package com.rogergcc.qrunlockclientapp.data


import com.rogergcc.qrunlockclientapp.data.network.QrEventClient
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.domain.model.toDomain
import javax.inject.Inject

class TheMovieDBRepository @Inject constructor(
    private val apiClient: QrEventClient
) {
    suspend fun getPopularMoviesFromApi(): List<AttendanceDomain> {
        val response = apiClient.getUserAttendanaceDetails()
        response.toString()
        return response.map { it.toDomain() }
    }


}