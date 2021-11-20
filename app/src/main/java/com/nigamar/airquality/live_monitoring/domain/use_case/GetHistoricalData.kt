package com.nigamar.airquality.live_monitoring.domain.use_case

import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHistoricalData @Inject constructor(
    private val cityRepo: CityRepo
) {

    operator fun invoke(cityName : String) : Flow<List<Double>> {
        return cityRepo.getCityHistoricalData(cityName).map {
            mapToDataList(it)
        }
    }

    private fun mapToDataList(cityList: List<City>) : List<Double> {
        return cityList.map {
            it.cityAqi
        }
    }

}