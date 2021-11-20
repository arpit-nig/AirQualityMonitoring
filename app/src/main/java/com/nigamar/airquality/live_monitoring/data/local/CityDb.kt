package com.nigamar.airquality.live_monitoring.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [City::class],
    version = 1
)
abstract class CityDb : RoomDatabase() {
    abstract fun getCityDao() : CityDao
}