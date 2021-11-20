package com.nigamar.airquality.live_monitoring.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nigamar.airquality.live_monitoring.data.dto.CityDto
import timber.log.Timber

@Entity(tableName = "cities_table")
data class City(
    @ColumnInfo(name = "city_name")
    val cityName : String,
    @ColumnInfo(name = "city_aqi")
    val cityAqi : Double,
    @ColumnInfo(name = "modified_at")
    val modifiedAt : Long
){
    @PrimaryKey (autoGenerate = true)
    var id : Int = 0
}

/** utility method to test */
fun List<City>.printList() {
    this.forEach {
        Timber.d("City is $it ")
    }
}
