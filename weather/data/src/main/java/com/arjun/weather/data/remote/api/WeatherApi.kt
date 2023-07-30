package com.arjun.weather.data.remote.api

import com.arjun.weather.data.remote.dto.CurrentLocationWeatherResult
import com.arjun.weather.data.remote.dto.LocationResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") key: String,
        @Query("q") query: String,
    ): List<LocationResult>


    @GET("search.json")
    suspend fun getCurrentLocationWeatherResult(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String = "yes",
    ): CurrentLocationWeatherResult


    companion object {
        const val BASE_URL = "http://api.weatherapi.com/v1/"
    }
}