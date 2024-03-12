package com.rogergcc.qrunlockclientapp.data.network

import android.content.res.Resources
import com.google.gson.Gson
import com.rogergcc.qrunlockclientapp.data.model.AttedancesRows
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
import retrofit2.Response
import javax.inject.Inject

class QrEventClient @Inject constructor(
    private val apiService: QrEventsApi) {

    suspend fun verifyRegisterUserAttendance(): List<AttedancesRows> {
           return try{
                val response = apiService.getVerifyUserAttendance()
                response.body()?.body?.attedancesRows ?: emptyList()
            }catch (e: QrEventInterceptor.NoInternetException){
//               Log.d(QrEventClient::class.java.simpleName, e.message, e)
               TimberAppLogger.e("[QrEventClient] (verifyRegisterUserAttendace) ${e.message}")
               emptyList()
            }
    }

    suspend fun registerAttendanceRecord(userCode: String): RegisterAttendanceResponse {
        try {
            TimberAppLogger.d("registerAttendanceRecord: userCode $userCode")

            val response = apiService.registerAttendanceRecord(UserAttendanceRequest(userCode, 1))

            response.errorBody()?.use {
                val errorBodyString = it.string()
                val errorResponse = Gson().fromJson(errorBodyString, RegisterAttendanceResponse::class.java)
                TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) Error response: $errorResponse")
                throw Exception(errorResponse.message)
            }

            TimberAppLogger.d("Raw response: $response")

            // Check if the request was successful (status code 2xx)
            if (response.isSuccessful) {
                val responseBody = response.body() as RegisterAttendanceResponse
                TimberAppLogger.i("Response body: $responseBody")
                return responseBody
            }

            // Si no es exitoso, se llegará a este punto y se lanzará la excepción
            TimberAppLogger.e("Unsuccessful response: ${response.code()} - ${response.message()}")
            throw handleHttpException(response)
        } catch (e: Exception) {
            // Handle other non-specific exceptions
            TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) ex $e")
            TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) ex.message ${e.message}")
            throw Exception(e.message)
        }

    }
    private fun handleHttpException(response: Response<*>): Exception {
        return when (response.code()) {
            404 -> {
                TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) Resource not found")
                Resources.NotFoundException("Resource not found")
            }
            // Handle other HTTP status codes as needed
            else -> {
                TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) HTTP status code: ${response.code()}")
                Exception("Error al registrar asistencia. HTTP status code: ${response.code()}")
            }
        }
    }
//    suspend fun registerAttendance(showResult: String): RegisterAttendanceResponse {
//        try {
//            // Realizar la llamada a la API con Retrofit
//            return apiService.registerAttendanceRecord(showResult) as RegisterAttendanceResponse
//        } catch (e: HttpException) {
//            // Manejar errores HTTP (por ejemplo, códigos 4xx o 5xx)
//            val errorBody = e.response()?.errorBody()?.string()
//            throw HttpException(e.response()!!)
//        } catch (e: Exception) {
//            // Manejar otros errores no específicos
//            throw Exception(e.message)
//        }
//    }

}