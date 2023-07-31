package com.arjun.weather.domain.repository

import com.arjun.weather.domain.model.Location
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WeatherRepository {

    suspend fun searchLocation(query: String): Result<List<Location>>

}