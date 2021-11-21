package com.nigamar.airquality

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.data.local.CityDao
import com.nigamar.airquality.live_monitoring.data.local.CityDb
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDbTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var cityDb: CityDb
    private lateinit var cityDao: CityDao
    private lateinit var cityList: List<City>

    @Before
    fun setup() {
        cityDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CityDb::class.java
        ).allowMainThreadQueries().build()
        cityDao = cityDb.getCityDao()
        val city1 = City("Delhi",300.12,1234L)
        val city2 = City("Noida",250.34,1000L)
        val city3 = City("Delhi",310.12,1250L)
        val city4 = City("Gurgaon",315.52,1150L)
        cityList = listOf(city1,city2,city3,city4)
    }

    @Test
    fun onQueryForCity_shouldReturnCityList(){
        val cityName = "Delhi"
        val city1 = City("Delhi",310.12,1250L)
        val city2 =  City("Delhi",300.12,1234L)
        val expectedList = listOf(city1,city2)
        runBlockingTest {
            cityDao.saveCityData(cityList)
            val actualList = cityDao.getCityData(cityName)
            assertThat(actualList).isEqualTo(expectedList)
        }
    }


}