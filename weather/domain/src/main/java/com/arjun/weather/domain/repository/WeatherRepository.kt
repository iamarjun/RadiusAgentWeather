package com.arjun.weather.domain.repository

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location

interface WeatherRepository {

    suspend fun searchLocation(query: String): Result<List<Location>>

    suspend fun getWeatherForLocation(slug: String): Result<CurrentLocationWeather>

}