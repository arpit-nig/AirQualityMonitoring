package com.nigamar.airquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo

class FakeCityRepo : CityRepo {

    private val fakeDb = hashMapOf<Int,List<City>>()

    private val _cityLiveData = MutableLiveData<List<City>>()
    val cityLiveData = _cityLiveData

    private val cacheList = mutableListOf<List<City>>()

    override suspend fun saveCityData(cityList: List<City>) {
        fakeDb[Math.random().toInt()] = cityList
    }

    override suspend fun getCityHistoricalData(cityName: String): List<City> {
        val cityList = mutableListOf<City>()
        fakeDb.values.forEach { list ->
            list.forEach { city ->
                if (city.cityName == cityName) cityList.add(city)
            }
        }
        return cityList
    }

    override fun cacheCurrentCityList(cityList: List<City>) {
        _cityLiveData.value  = cityList
    }

    override fun getCurrentCityListFromCache(): LiveData<List<City>> {
        return cityLiveData
    }
}