package com.nigamar.airquality.live_monitoring.domain.use_case


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nigamar.airquality.FakeCityRepo
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.domain.model.AirQualityMatcher
import com.nigamar.airquality.live_monitoring.domain.model.CityListItem
import com.nigamar.airquality.live_monitoring.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetLiveDataTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var fakeCityRepo: FakeCityRepo
    private lateinit var getLiveData: GetLiveData
    private lateinit var cityList1 : List<City>
    private lateinit var cityList2 : List<City>

    @Before
    fun setUp() {
        fakeCityRepo = FakeCityRepo()
        fakeCityRepo.cityLiveData.observeForever {  }
        getLiveData = GetLiveData(fakeCityRepo)
        val city1 = City("Delhi",300.12,1234L)
        val city2 = City("Noida",250.34,1000L)
        val city3 = City("Delhi",310.12,1250L)
        val city4 = City("Gurgaon",315.52,1150L)
        cityList1 = listOf(city1,city2)
        cityList2 = listOf(city3,city4)
    }

    @Test
    fun shouldReturnLatestInsertedDataInTheCache(){
        val cityListItem1 =
            CityListItem("Delhi", "310.12", AirQualityMatcher.getAirQuality(310.12), "Last Updated 05:30 AM")
        val cityListItem2 =
            CityListItem("Gurgaon", "315.52", AirQualityMatcher.getAirQuality(315.52), "Last Updated 05:30 AM")
        val expected = listOf(cityListItem1, cityListItem2)
        fakeCityRepo.cacheCurrentCityList(cityList1)
        fakeCityRepo.cacheCurrentCityList(cityList2)
        val actual = getLiveData().getOrAwaitValue()
        assertThat(actual).isEqualTo(expected)
    }
}