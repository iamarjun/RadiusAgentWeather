package com.arjun.weather.domain.usecases

import com.arjun.weather.domain.model.Location
import com.arjun.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchLocation @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(query: String) : Result<List<Location>> {
        return repository.searchLocation(query = query)
    }

}