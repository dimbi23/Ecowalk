package com.wanowanconsult.ecowalk

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EcowalkApplication : Application(), Configuration.Provider {
    init {
        instance = this
    }

    companion object {
        private var instance: EcowalkApplication? = null

        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()
}