package com.arjun.weather.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract suspend fun insertWeather(foodEntity: WeatherEntity)
//
//    @Delete
//    abstract suspend fun deleteWeather(foodEntity: WeatherEntity)
//
//    @Query("SELECT * FROM trackedfoodentity WHERE dayOfMonth = :day AND month = :month AND year = :year")
//    abstract fun getFoodForTheDate(day: Int, month: Int, year: Int): Flow<List<WeatherEntity>>

}