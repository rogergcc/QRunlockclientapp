package com.rogergcc.qrunlockclientapp.domain

import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.data.TheMovieDBRepository
import javax.inject.Inject


class GetVerifyUserRegister @Inject constructor(private val repository: TheMovieDBRepository){

    suspend operator fun invoke(): List<AttendanceDomain>{
        val movies = repository.getPopularMoviesFromApi()
        return movies
//        if(movies.isNotEmpty()){
//            repository.clearMovies()
//            return movies
//        }else{
//            return repository.getPopularMoviesFromLocalDB()
//        }
    }

}