package com.nigamar.airquality.live_monitoring.data

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nigamar.airquality.live_monitoring.data.dto.CityDto
import com.nigamar.airquality.live_monitoring.data.dto.printList
import com.nigamar.airquality.live_monitoring.data.dto.toCityList
import com.nigamar.airquality.live_monitoring.data.local.CityDao
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CityService : LifecycleService() {

    private val binder = LocalBinder()

    @Inject
    lateinit var client : OkHttpClient

    @Inject
    lateinit var webSocketRequest : Request

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var cityRepo: CityRepo

    private val listType = object : TypeToken<List<CityDto>>(){}.type

    private lateinit var webSocket: WebSocket

    private val socketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Timber.d("Connection Open....")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val cityDtoList = gson.fromJson(text,listType) as List<CityDto>
            if (cityDtoList.isNotEmpty()){
                lifecycleScope.launch {
                    val cityList = cityDtoList.toCityList() // map the dto to the list of cities
                    cityRepo.cacheCurrentCityList(cityList) // expose data as live data to the application
                    cityRepo.saveCityData(cityList) // cache in the local db to get the historical data
                }
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Timber.d("Connection Closed.....")
        }
    }

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
        webSocket = client.newWebSocket(webSocketRequest,socketListener)
    }

    fun stopUpdates() {
        // close the socket to close the feeds
        Timber.d("Stopping updates....")
        webSocket.cancel()
    }
}