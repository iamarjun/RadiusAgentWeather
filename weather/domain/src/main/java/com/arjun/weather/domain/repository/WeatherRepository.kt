package com.arjun.weather.domain.repository

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getAllCachedLocation(): Flow<List<CurrentLocationWeather>>

    suspend fun searchLocation(query: String): Result<List<Location>>

    suspend fun getWeatherForLocation(slug: String): Result<CurrentLocationWeather>

    suspend fun saveLocationToCache(currentLocationWeather: CurrentLocationWeather): Result<Boolean>

    suspend fun isLocationPresent(latlon: String): Result<Boolean>

    suspend fun deleteLocationFromCache(latlon: String): Result<Boolean>

}