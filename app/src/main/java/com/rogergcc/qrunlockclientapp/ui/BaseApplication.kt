package com.rogergcc.qrunlockclientapp.ui

import android.app.Application
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
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