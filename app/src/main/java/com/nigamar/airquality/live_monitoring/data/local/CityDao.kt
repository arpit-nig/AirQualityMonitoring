package com.nigamar.airquality.live_monitoring.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    /**
     * method to save the city data into the db
     * */
    @Insert
    suspend fun saveCityData(cityList: List<City>)

    /**
     * method to get the city data from the db
     * this method returns the historical data , can return up to 15 values
     * which can be plotted to se the chart
     * */
    @Query("select * from cities_table where city_name = :cityName order by modified_at desc limit 15")
    suspend fun getCityData(cityName : String) : List<City>

}