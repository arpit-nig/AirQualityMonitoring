package com.nigamar.airquality.live_monitoring.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.nigamar.airquality.R
import com.nigamar.airquality.live_monitoring.data.local.CityDao
import com.nigamar.airquality.live_monitoring.data.local.printList
import com.nigamar.airquality.live_monitoring.ui.fragment.LiveTrackerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}