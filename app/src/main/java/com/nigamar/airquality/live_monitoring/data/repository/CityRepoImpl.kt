package com.nigamar.airquality.live_monitoring.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.data.local.CityDao
import com.nigamar.airquality.live_monitoring.data.local.InMemoryCache
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepoImpl @Inject constructor(
    private val cityDao: CityDao
) : CityRepo {

    override suspend fun saveCityData(cityList: List<City>) {
        cityDao.saveCityData(cityList)
    }

    override fun getCityHistoricalData(cityName: String): Flow<List<City>> {
        return cityDao.getCityData(cityName)
    }

    override fun cacheCurrentCityList(cityList: List<City>) {
        InMemoryCache.save(cityList)
    }

    override fun getCurrentCityListFromCache(): LiveData<List<City>> {
        return InMemoryCache.get()
    }
}