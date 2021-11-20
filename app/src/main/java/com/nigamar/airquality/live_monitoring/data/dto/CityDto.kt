package com.nigamar.airquality.live_monitoring.data.dto

import com.nigamar.airquality.live_monitoring.data.local.City

data class CityDto(
    val cityName : String,
    val cityAqi : String
)

fun CityDto.toCity() : City {
    return City(
        this.cityName,
        this.cityAqi.toDouble(),
        System.currentTimeMillis()
    )
}
