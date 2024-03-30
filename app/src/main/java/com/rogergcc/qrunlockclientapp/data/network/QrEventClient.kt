package com.rogergcc.qrunlockclientapp.data.network

import android.content.res.Resources
import com.google.gson.Gson
import com.rogergcc.qrunlockclientapp.data.network.request.UserAttendanceRequest
import com.rogergcc.qrunlockclientapp.data.network.response.AttedancesRows
import com.rogergcc.qrunlockclientapp.data.network.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.presentation.extensions.fromJson
import com.rogergcc.qrunlockclientapp.presentation.helper.TimberAppLogger
import com.rogergcc.qrunlockclientapp.presentation.scanner.QrScanImage
import retrofit2.Response
import javax.inject.Inject

class QrEventClient @Inject constructor(
    private val apiService: QrEventsApi,
) {

    suspend fun verifyRegisterUserAttendance(): List<AttedancesRows> {
        return try {
            val response = apiService.getVerifyUserAttendance()
            response.body()?.body?.attedancesRows ?: emptyList()
        } catch (e: QrEventInterceptor.NoInternetException) {
//               Log.d(QrEventClient::class.java.simpleName, e.message, e)
            TimberAppLogger.e("[QrEventClient] (verifyRegisterUserAttendace) ${e.message}")
            emptyList()
        }
    }

    suspend fun registerAttendanceRecord(userCode: String): RegisterAttendanceResponse {
        try {
            TimberAppLogger.d("registerAttendanceRecord: userCode $userCode")

            val response = apiService.registerAttendanceRecord(UserAttendanceRequest(userCode, 1))
            TimberAppLogger.d("Raw response: $response")
            response.errorBody()?.use {
                val errorBodyString = it.string()

//                val errorResponse = Gson().fromJson(errorBodyString, RegisterAttendanceResponse::class.java)
                val errorResponse : RegisterAttendanceResponse = Gson().fromJson(errorBodyString)
                TimberAppLogger.e("[QrEventClient] (registerAttendanceRecord) Error response: $errorResponse")
//                throw Exception(errorResponse.message)
                throw handleHttpException(response)
            }
            if (!response.isSuccessful) {
                TimberAppLogger.e("Unsuccessful response: ${response.code()} - ${response.message()}")
                throw handleHttpException(response)
            }

            val responseBody = response.body() as RegisterAttendanceResponse
            TimberAppLogger.i("Response body: $responseBody")
            return responseBody
        } catch (e: Exception) {
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