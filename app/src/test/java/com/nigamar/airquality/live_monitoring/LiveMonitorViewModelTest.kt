package com.nigamar.airquality.live_monitoring

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nigamar.airquality.FakeCityRepo
import com.nigamar.airquality.live_monitoring.data.local.City
import com.nigamar.airquality.live_monitoring.domain.use_case.GetHistoricalData
import com.nigamar.airquality.live_monitoring.domain.use_case.GetLiveData
import com.nigamar.airquality.live_monitoring.ui.view_model.LiveMonitorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LiveMonitorViewModelTest {

    @get:Rule
    val runner = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    private lateinit var fakeCityRepo: FakeCityRepo
    private lateinit var getHistoricalData: GetHistoricalData
    private lateinit var getLiveData: GetLiveData
    private lateinit var liveMonitorViewModel: LiveMonitorViewModel
    private lateinit var cityList : List<City>

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        fakeCityRepo = FakeCityRepo()
        getHistoricalData = GetHistoricalData(fakeCityRepo)
        getLiveData = GetLiveData(fakeCityRepo)
        liveMonitorViewModel = LiveMonitorViewModel(getLiveData,getHistoricalData)
        val city1 = City("Delhi",300.12,1234L)
        val city2 = City("Noida",250.34,1000L)
        val city3 = City("Delhi",310.12,1250L)
        val city4 = City("Gurgaon",315.52,1150L)
        cityList = listOf(city1,city2,city3,city4)
    }

    @Test
    fun onLoadState_shouldReturnCorrectLiveDataValueForViewToObserve(){
        val expected = listOf(300.12f,310.12f)
        runBlockingTest {
            fakeCityRepo.saveCityData(cityList)
            liveMonitorViewModel.loadState("Delhi")
            val actual = liveMonitorViewModel.historicalData.getOrAwaitValue()
            assertThat(actual).isEqualTo(expected)
        }
    }

}