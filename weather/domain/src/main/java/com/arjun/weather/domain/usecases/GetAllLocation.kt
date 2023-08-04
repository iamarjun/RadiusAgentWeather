package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllLocation @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): Flow<List<CurrentLocationWeather>> {
        return withContext(Dispatchers.IO) { repository.getAllCachedLocation() }
    }
}