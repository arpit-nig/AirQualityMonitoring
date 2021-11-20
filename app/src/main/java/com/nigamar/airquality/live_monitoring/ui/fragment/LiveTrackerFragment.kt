package com.nigamar.airquality.live_monitoring.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nigamar.airquality.R
import com.nigamar.airquality.live_monitoring.ui.adapters.CityListAdapter
import com.nigamar.airquality.live_monitoring.ui.view_model.LiveMonitorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_live_tracker.*

@AndroidEntryPoint
class LiveTrackerFragment : Fragment(R.layout.fragment_live_tracker) {

    private val liveMonitorViewModel : LiveMonitorViewModel by viewModels()

    private lateinit var cityListAdapter: CityListAdapter

    override fun onStart() {
        super.onStart()
        initView()
        setUpAdapter()
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun initView() {
        cityListAdapter = CityListAdapter()
        live_city_list.apply {
            adapter = cityListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpAdapter() {
        cityListAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("cityName",it.cityName)
            }
            findNavController().navigate(
                R.id.action_liveTrackerFragment_to_historicalDataFragment ,
                bundle
            )
        }
    }

    private fun observeChanges() {
        liveMonitorViewModel.liveCityData.observe(this, {
            cityListAdapter.differ.submitList(it)
        })
    }
}