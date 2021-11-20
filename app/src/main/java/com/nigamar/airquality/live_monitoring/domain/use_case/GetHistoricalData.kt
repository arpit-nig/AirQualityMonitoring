package com.nigamar.airquality.live_monitoring.domain.use_case

import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import javax.inject.Inject

class GetHistoricalData @Inject constructor(
    private val cityRepo: CityRepo
) {

    suspend operator fun invoke(cityName : String) : List<Float> {
        return cityRepo.getCityHistoricalData(cityName).map {
            it.cityAqi.toFloat()
        }
    }

}