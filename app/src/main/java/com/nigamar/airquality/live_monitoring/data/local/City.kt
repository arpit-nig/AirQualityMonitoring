package com.nigamar.airquality.live_monitoring.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
