package com.nigamar.airquality.live_monitoring.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/** Simple holder to expose the current values to the application as a live data */
object InMemoryCache {

    private val _cityListLiveData = MutableLiveData<List<City>>()
    private val cityListLiveData = _cityListLiveData as LiveData<List<City>>

    fun save(cityList: List<City>) {
        _cityListLiveData.postValue(cityList)
    }

    fun  get() = cityListLiveData

}