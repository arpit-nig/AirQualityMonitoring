package com.nigamar.airquality.live_monitoring.domain.model

import com.nigamar.airquality.R
import com.nigamar.airquality.common.Constants

sealed class AirQuality() {
     data class Good(
        val name: String,
        val image: Int
    ) : AirQuality()

    data class Satisfactory(
        val name: String,
        val image: Int
    ) : AirQuality()

    data class Moderate(
        val name: String,
        val image: Int
    ) : AirQuality()

    data class Poor(
        val name: String,
        val image: Int
    ) : AirQuality()

    data class VeryPoor(
        val name: String,
        val image: Int
    ) : AirQuality()

    data class Severe(
        val name: String,
        val image: Int
    ) : AirQuality()
}

object AirQualityMatcher {

    private val good = AirQuality.Good(Constants.GOOD, R.drawable.air_quality_good)
    private val satisfactory = AirQuality.Satisfactory(Constants.SATISFACTORY,R.drawable.air_quality_satisfactory)
    private val moderate = AirQuality.Moderate(Constants.MODERATE,R.drawable.air_quality_moderate)
    private val poor = AirQuality.Poor(Constants.POOR,R.drawable.air_quality_poor)
    private val veryPoor = AirQuality.VeryPoor(Constants.VERY_POOR,R.drawable.air_quality_very_poor)
    private val severe = AirQuality.Severe(Constants.SEVERE,R.drawable.air_quality_severe)

    fun getAirQuality(cityAqi: Double): AirQuality {
        return when(cityAqi.toInt()) {
            in 0..50 -> good
            in 51..100 -> satisfactory
            in 101..200 -> moderate
            in 201..300 -> poor
            in 301..400 -> veryPoor
            in 401..500 -> severe
            else -> good
        }
    }

    fun getAirQualityColor( airQuality: AirQuality ) : Int {
        return when(airQuality) {
            is AirQuality.Good -> R.color.quality_good
            is AirQuality.Moderate -> R.color.quality_moderate
            is AirQuality.Poor -> R.color.quality_poor
            is AirQuality.Satisfactory -> R.color.quality_satisfactory
            is AirQuality.Severe -> R.color.quality_severe
            is AirQuality.VeryPoor -> R.color.quality_very_poor
        }
    }

    fun getAirQualityImage( airQuality: AirQuality ) : Int {
        return when(airQuality) {
            is AirQuality.Good -> good.image
            is AirQuality.Moderate -> moderate.image
            is AirQuality.Poor -> poor.image
            is AirQuality.Satisfactory -> satisfactory.image
            is AirQuality.Severe -> severe.image
            is AirQuality.VeryPoor -> veryPoor.image
        }
    }

}
