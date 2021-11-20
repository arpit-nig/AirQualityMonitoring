package com.nigamar.airquality.live_monitoring.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert
    suspend fun saveCityData(cityList: List<City>)

    @Query("select * from cities_table where city_name = :cityName order by modified_at limit 15")
    fun getCityData(cityName : String) : Flow<List<City>>

}