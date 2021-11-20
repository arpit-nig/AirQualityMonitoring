package com.nigamar.airquality.live_monitoring.data.dto

import com.google.gson.annotations.SerializedName
import com.nigamar.airquality.live_monitoring.data.local.City
import timber.log.Timber

data class CityDto(
    @SerializedName(value = "city")
    val cityName : String,
    @SerializedName(value = "aqi")
    val cityAqi : String
)

/** utility method to test */
fun List<CityDto>.printList() {
    this.forEach {
        Timber.d("City is $it ")
    }
}

/** map the city dto to the list of cities */
fun List<CityDto>.toCityList() : List<City> {
    return this.map {
        it.toCity()
    }.toList()
}

/** map a single dto to a city */
private fun CityDto.toCity() : City {
    return City(
        this.cityName,
        this.cityAqi.toDouble(),
        System.currentTimeMillis()
    )
}
