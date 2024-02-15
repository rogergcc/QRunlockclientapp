package com.rogergcc.qrunlockclientapp.domain

import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.data.TheAttendanceRepository
import javax.inject.Inject


class VerifyUserRegisterUseCase @Inject constructor(private val repository: TheAttendanceRepository){

    suspend operator fun invoke(): List<AttendanceDomain>{
        val attendaceUser = repository.getAttendanceData()
        return attendaceUser
//        if(movies.isNotEmpty()){
//            repository.clearMovies()
//            return movies
//        }else{
//            return repository.getPopularMoviesFromLocalDB()
//        }
    }

}