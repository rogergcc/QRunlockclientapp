package com.rogergcc.qrunlockclientapp.domain.attendance

import com.rogergcc.qrunlockclientapp.data.TheAttendanceRepository
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import javax.inject.Inject


/**
 * Created on marzo.
 * year 2024 .
 */
class RegisterAttendanceUseCase @Inject constructor(private val repository: TheAttendanceRepository){

    suspend operator fun invoke(userCode: String): RegisterAttendanceResponse {
        return repository.registerAttendance(userCode)
//        if(movies.isNotEmpty()){
//            repository.clearMovies()
//            return movies
//        }else{
//            return repository.getPopularMoviesFromLocalDB()
//        }
    }

}