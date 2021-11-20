package com.nigamar.airquality.live_monitoring.data.local

import androidx.collection.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber

object InMemoryCache {

    private val cache = LruCache<String,List<City>>(10)

    private const val LABEL = "CurrentList"

    private val _cityListLiveData = MutableLiveData<List<City>>()
    private val cityListLiveData = _cityListLiveData as LiveData<List<City>>

    fun save(cityList: List<City>) {
//        Timber.d("Saving to cache ${cityList.size}")
//        cache.put(LABEL,cityList)
        _cityListLiveData.postValue(cityList)
    }

//    fun get() = flow {
//        Timber.d("Getting from cache ... ")
//        emit(cache.get(LABEL) ?: emptyList())
//    }.flowOn(Dispatchers.Default)

    fun  get() = cityListLiveData

}