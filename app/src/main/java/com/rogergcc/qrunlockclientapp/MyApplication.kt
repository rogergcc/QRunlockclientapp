package com.rogergcc.qrunlockclientapp

import android.app.Application
import com.rogergcc.qrunlockclientapp.helper.TimberAppLogger


/**
 * Created on junio.
 * year 2023 .
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        TimberAppLogger.init()

//        FirebaseApp.initializeApp(this)
//        FirebaseInstallations.getInstance().id.addOnCompleteListener { task: Task<String?> ->
//            if (task.isSuccessful) {
//                val token = task.result
//                Log.e("token ---->>", token!!)
//
//                val mEditor = mSharedPrefs.edit()
//                mEditor.putString(AppConstants.FCM_TOKEN, token)
//                mEditor.apply()
//
//            }
//        }
    }
}