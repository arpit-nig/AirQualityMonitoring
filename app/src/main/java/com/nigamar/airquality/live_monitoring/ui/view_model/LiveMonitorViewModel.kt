package com.nigamar.airquality.live_monitoring.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nigamar.airquality.live_monitoring.domain.use_case.GetHistoricalData
import com.nigamar.airquality.live_monitoring.domain.use_case.GetLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveMonitorViewModel @Inject constructor(
    private val getLiveData: GetLiveData ,
    private val getHistoricalData: GetHistoricalData
) : ViewModel() {

    private val _historicalData = MutableLiveData<List<Float>>()
    val historicalData = _historicalData

    // use this live data to get updates on the live list
    val liveCityData = getLiveData()

    private fun getData(cityName : String) = viewModelScope.launch {
        val response = getHistoricalData(cityName)
        // get the list of all the values for the city
        _historicalData.postValue(response)
    }

    fun loadState(cityName : String){
        getData(cityName)
    }

}