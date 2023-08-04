package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveLocationFromCache @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(latlon: String): Result<Boolean> {
        return withContext(Dispatchers.IO) { repository.deleteLocationFromCache(latlon) }
    }
}