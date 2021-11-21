package com.nigamar.airquality.live_monitoring.domain.model

data class CityListItem (
    val cityName : String ,
    val cityAqi : String ,
    val airQuality : AirQuality ,
    val airQualityText : String,
    val modifiedTime : String
)
