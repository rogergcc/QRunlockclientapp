package com.rogergcc.qrunlockclientapp.presentation

import android.app.Application
import com.rogergcc.qrunlockclientapp.presentation.helper.TimberAppLogger
import dagger.hilt.android.HiltAndroidApp


/**
 * Created on junio.
 * year 2023 .
 */
@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TimberAppLogger.init()

    }
}