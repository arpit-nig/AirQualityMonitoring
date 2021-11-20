package com.nigamar.airquality.live_monitoring.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.domain.model.AirQualityMatcher
import com.nigamar.airquality.live_monitoring.domain.model.CityListItem
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetLiveData @Inject constructor(
    private val cityRepo: CityRepo
){

    private val timeFormatter by lazy { SimpleDateFormat("hh:mm a" , Locale.getDefault()) }

    operator fun invoke() : LiveData<List<CityListItem>> {
        val cityList = cityRepo.getCurrentCityListFromCache()
        return Transformations.map(cityList) {
            mapCityListToCityItem(it)
        }
    }

    private fun mapCityListToCityItem(cityList: List<City>?) : List<CityListItem> {
        return cityList?.map {
             toCityView(it)
        }?.toList() ?: emptyList()
    }

    private fun toCityView(city: City): CityListItem {
        return CityListItem(
            cityName = city.cityName,
            cityAqi = String.format("%.2f",city.cityAqi),
            airQuality = AirQualityMatcher.getAirQuality(city.cityAqi),
            modifiedTime = getModifiedText(city.modifiedAt)
        )
    }

    private fun getModifiedText(modifiedAt: Long): String {
        val time = timeFormatter.format(Date(modifiedAt))
        return "Last Updated $time"
    }
}