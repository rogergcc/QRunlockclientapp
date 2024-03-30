package com.rogergcc.qrunlockclientapp.domain.usecases

import com.rogergcc.qrunlockclientapp.domain.model.AttendanceEntity
import com.rogergcc.qrunlockclientapp.domain.repository.IEventAttendanceRepository
import javax.inject.Inject


class VerifyUserRegisterUseCase @Inject constructor(private val repository: IEventAttendanceRepository){

    suspend operator fun invoke(): List<AttendanceEntity>{
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