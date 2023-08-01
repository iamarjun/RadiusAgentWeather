package com.arjun.weather.data.repository

import com.arjun.weather.data.local.db.WeatherDao
import com.arjun.weather.data.mapper.DomainMapper.toCurrentLocationWeather
import com.arjun.weather.data.mapper.DomainMapper.toLocation
import com.arjun.weather.data.remote.api.WeatherApi
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location
import com.arjun.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun searchLocation(query: String): Result<List<Location>> {
        return try {
            val location = weatherApi.searchLocation(query = query)
            Result.success(location.map { it.toLocation() })
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun getWeatherForLocation(slug: String): Result<CurrentLocationWeather> {
        return try {
            val currentLocationWeatherResult =
                weatherApi.getCurrentLocationWeatherForecastResult(query = slug)
            Result.success(currentLocationWeatherResult.toCurrentLocationWeather())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}