package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherInfo @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(slug: String): Result<CurrentLocationWeather> {
        return withContext(Dispatchers.IO) { repository.getWeatherForLocation(slug) }
    }
}