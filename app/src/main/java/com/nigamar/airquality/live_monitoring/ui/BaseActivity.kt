package com.nigamar.airquality.live_monitoring.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.nigamar.airquality.live_monitoring.data.CityService
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {

    private lateinit var service : CityService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder : IBinder?) {
            val b = binder as CityService.LocalBinder
            service = b.getService()
            Timber.d("Service binding successful ")
            service.startUpdates()
        }

        override fun onServiceDisconnected(name : ComponentName?) {
            Timber.d("Service un binding successful ")
            service.stopUpdates()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLiveFeed()
    }

    private fun startLiveFeed() {
        val intent = Intent(this, CityService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private fun stopLiveFeed() {
        unbindService(connection)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLiveFeed()
    }
}