package com.nigamar.airquality.live_monitoring.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.nigamar.airquality.R
import com.nigamar.airquality.live_monitoring.ui.view_model.LiveMonitorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_historical_data.*
import timber.log.Timber

@AndroidEntryPoint
class HistoricalDataFragment : Fragment(R.layout.fragment_historical_data) {

    private val liveMonitorViewModel : LiveMonitorViewModel by viewModels()

    private val args : HistoricalDataFragmentArgs by navArgs()

    lateinit var cityName : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityName = args.cityName
        initializeChart()
        Timber.d("Get historical data for $cityName ")
        liveMonitorViewModel.loadState(cityName)
    }

    private fun initializeChart() {
        line_chart.data = LineData()
        line_chart.description = Description().also {
            it.text = "AQI $cityName"
            it.textSize = 20f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.textColor = requireContext().getColor(R.color.white)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        liveMonitorViewModel.historicalData.observe(this, { dataPoints ->
                updateChartData(dataPoints)
        })
    }

    private fun updateChartData(dataPoint: List<Float>) {
          val lineData = line_chart.lineData
          if (lineData != null) {
              lineData.clearValues()
              lineData.addDataSet(createSet(dataPoint))
              line_chart.clear()
              line_chart.data = lineData
          }
        line_chart.invalidate()
    }


    private fun createSet(dataPoint: List<Float>): ILineDataSet {
        val entries = ArrayList<Entry>()
        for (i in dataPoint.indices) {
            entries.add(Entry(i.toFloat(),dataPoint[i]))
        }
        val set = LineDataSet(entries,null)
        set.apply {
            lineWidth = 3f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = requireContext().getColor(R.color.white)
                valueTextColor = requireContext().getColor(R.color.white)
            }
            valueTextSize = 10f
        }
        return set
    }
}