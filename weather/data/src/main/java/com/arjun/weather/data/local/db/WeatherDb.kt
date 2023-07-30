package com.arjun.weather.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: WeatherDb? = null
        private val lock = Any()
        fun getInstance(context: Context) = INSTANCE ?: synchronized(lock) {
            INSTANCE ?: Room.databaseBuilder(context, WeatherDb::class.java, "weather_db").build()
                .also {
                    INSTANCE = it
                }
        }
    }
}