package com.nigamar.airquality.live_monitoring.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nigamar.airquality.live_monitoring.data.local.City
import kotlinx.coroutines.flow.Flow

interface CityRepo {
    /** to insert data in db */
    suspend fun saveCityData(cityList: List<City>)

    /** To get the historical data for a particular city */
    fun getCityHistoricalData(cityName : String) : Flow<List<City>>

    /** to insert the latest list into in-memory cache */
    fun cacheCurrentCityList(cityList: List<City>)

    /** to get the latest list from in-memory cache */
    fun getCurrentCityListFromCache() : LiveData<List<City>>

}