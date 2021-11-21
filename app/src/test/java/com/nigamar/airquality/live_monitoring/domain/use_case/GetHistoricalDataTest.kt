package com.nigamar.airquality.live_monitoring.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nigamar.airquality.FakeCityRepo
import com.nigamar.airquality.live_monitoring.data.local.City
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetHistoricalDataTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var fakeCityRepo: FakeCityRepo
    private lateinit var getHistoricalData: GetHistoricalData
    private lateinit var cityList : List<City>

    @Before
    fun setup(){
        fakeCityRepo = FakeCityRepo()
        getHistoricalData = GetHistoricalData(fakeCityRepo)
        val city1 = City("Delhi",300.12,1234L)
        val city2 = City("Noida",250.34,1000L)
        val city3 = City("Delhi",310.12,1250L)
        val city4 = City("Gurgaon",315.52,1150L)
        cityList = listOf(city1,city2,city3,city4)
    }

    @Test
    fun whenSuppliedWithCityName_shouldReturnAqiList(){
        val expected = listOf(300.12f,310.12f)
        runBlockingTest {
            fakeCityRepo.saveCityData(cityList)
            val cityName = "Delhi"
            val actual = getHistoricalData(cityName)
            assertThat(actual).isEqualTo(expected)
        }
    }


}