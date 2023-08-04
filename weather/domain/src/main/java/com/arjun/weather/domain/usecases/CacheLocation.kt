package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheLocation @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(currentLocationWeather: CurrentLocationWeather): Result<Boolean> {
        return withContext(Dispatchers.IO) { repository.saveLocationToCache(currentLocationWeather) }
    }
}