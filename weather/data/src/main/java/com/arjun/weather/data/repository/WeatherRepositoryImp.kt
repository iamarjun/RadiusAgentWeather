package com.arjun.weather.data.repository

import com.arjun.weather.data.local.db.WeatherDao
import com.arjun.weather.data.mapper.DomainMapper.toCurrentLocationWeather
import com.arjun.weather.data.mapper.DomainMapper.toLocation
import com.arjun.weather.data.mapper.DomainMapper.toLocationEntity
import com.arjun.weather.data.remote.api.WeatherApi
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location
import com.arjun.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val weatherApi: WeatherApi, private val weatherDao: WeatherDao
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

    override suspend fun saveLocationToCache(currentLocationWeather: CurrentLocationWeather): Result<Boolean> {
        return try {
            val id = weatherDao.insertLocation(currentLocationWeather.toLocationEntity())
            Result.success(id != null)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun isLocationPresent(latlon: String): Result<Boolean> {
        return try {
            val exists = weatherDao.locationExists(latlon)
            Result.success(exists == 1)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun deleteLocationFromCache(latlon: String): Result<Boolean> {
        return try {
            val isDeleted = weatherDao.deleteLocation(latlon)
            Result.success(isDeleted > 0)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}