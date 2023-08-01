package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherInfo @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(slug: String): Result<CurrentLocationWeather> {
        return repository.getWeatherForLocation(slug)
    }
}