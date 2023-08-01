package com.arjun.weather.data.remote.api

import com.arjun.weather.data.remote.dto.CurrentLocationWeatherResult
import com.arjun.weather.data.remote.dto.LocationResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") key: String = "eb462722f37d4e1b8cf82924233007",
        @Query("q") query: String,
    ): List<LocationResult>


    @GET("current.json")
    suspend fun getCurrentLocationWeatherResult(
        @Query("key") key: String = "eb462722f37d4e1b8cf82924233007",
        @Query("q") query: String,
        @Query("aqi") aqi: String = "yes",
    ): CurrentLocationWeatherResult

    @GET("forecast.json")
    suspend fun getCurrentLocationWeatherForecastResult(
        @Query("key") key: String = "eb462722f37d4e1b8cf82924233007",
        @Query("q") query: String,
        @Query("days") days: Int = 5,
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "yes",
    ): CurrentLocationWeatherResult


    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }
}