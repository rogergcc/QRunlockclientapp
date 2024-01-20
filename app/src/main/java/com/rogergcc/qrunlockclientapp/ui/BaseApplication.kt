package com.rogergcc.qrunlockclientapp.ui

import android.app.Application
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger


/**
 * Created on junio.
 * year 2023 .
 */
class BaseApplication : Application() {

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