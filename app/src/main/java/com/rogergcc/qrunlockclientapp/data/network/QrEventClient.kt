package com.rogergcc.qrunlockclientapp.data.network

import com.rogergcc.qrunlockclientapp.data.model.AttedancesRows
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
import javax.inject.Inject

class QrEventClient @Inject constructor(private val api: QrEventsApi) {

    suspend fun verifyRegisterUserAttendance(): List<AttedancesRows> {
           return try{
                val response = api.getVerifyUserAttendance()
                response.body()?.body?.attedancesRows ?: emptyList()
            }catch (e: QrEventInterceptor.NoInternetException){
//               Log.d(QrEventClient::class.java.simpleName, e.message, e)
               TimberAppLogger.e("[QrEventClient] (verifyRegisterUserAttendace) ${e.message}")
               emptyList()
            }
    }

}