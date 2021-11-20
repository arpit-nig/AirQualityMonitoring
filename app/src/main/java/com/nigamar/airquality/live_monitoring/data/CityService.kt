package com.nigamar.airquality.live_monitoring.data

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import timber.log.Timber

class CityService : LifecycleService() {

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): CityService = this@CityService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    fun startUpdates() {
        // establish a new web socket connection to get the feeds
        Timber.d("Starting updates....")
    }

    fun stopUpdates() {
        // close the socket to close the feeds
        Timber.d("Stopping updates....")
    }
}