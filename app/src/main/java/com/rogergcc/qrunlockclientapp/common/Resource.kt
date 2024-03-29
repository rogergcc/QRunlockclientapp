package com.rogergcc.qrunlockclientapp.common

import okhttp3.ResponseBody


/**
 * Created on marzo.
 * year 2024 .
 */
sealed class Resource<out T> {

 data class Success<out T>(val value: T) : Resource<T>()

 data class Failure(
  val isNetworkError: Boolean,
  val errorCode: Int?,
  val errorBody: ResponseBody?
 ) : Resource<Nothing>()

 object Loading : Resource<Nothing>()
}