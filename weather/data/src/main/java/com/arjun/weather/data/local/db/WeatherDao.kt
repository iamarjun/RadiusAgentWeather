package com.arjun.weather.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjun.weather.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLocation(locationEntity: LocationEntity): Long?

    @Query("SELECT EXISTS(SELECT 1 FROM locationentity WHERE latLon= :latlon)")
    abstract suspend fun locationExists(latlon: String): Int

    @Query("DELETE FROM LocationEntity WHERE latLon = :latlon")
    abstract fun deleteLocation(latlon: String): Int

    @Query("SELECT * FROM LocationEntity")
    abstract fun getLocations(): Flow<List<LocationEntity>>
}