package com.nigamar.airquality

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AirQualityMonitoring : Application() {
    override fun onCreate() {
        super.onCreate()
        // plant a debug tree for logging
        Timber.plant(Timber.DebugTree())
    }
}