package com.nigamar.airquality

import android.app.Application
import timber.log.Timber

class AirQualityMonitoring : Application() {
    override fun onCreate() {
        super.onCreate()
        // plant a debug tree for logging
        Timber.plant(Timber.DebugTree())
    }
}