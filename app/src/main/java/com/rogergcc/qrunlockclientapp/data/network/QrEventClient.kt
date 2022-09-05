package com.rogergcc.qrunlockclientapp.data.network

import android.util.Log
import com.rogergcc.qrunlockclientapp.data.model.AttedancesRows
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import javax.inject.Inject

class QrEventClient @Inject constructor(private val api: QrEventsApi) {

    suspend fun verifyRegisterUserAttendace(): List<AttedancesRows> {
           return try{
                val response = api.getVerifyUserAttendance()
                response.body()?.body?.attedancesRows ?: emptyList()
            }catch (e: QrEventInterceptor.NoInternetException){
               Log.d(QrEventClient::class.java.simpleName, e.message, e)
               emptyList()
            }
    }

}