package com.arjun.weather.data.mapper

import com.arjun.weather.data.remote.dto.CurrentLocationWeatherResult
import com.arjun.weather.data.remote.dto.LocationResult
import com.arjun.weather.data.remote.dto.WeatherResult
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location
import com.arjun.weather.domain.model.Weather

object DomainMapper {
    fun LocationResult.toLocation() = Location(
        country = country,
        id = id,
        lat = lat,
        lon = lon,
        name = name,
        region = region,
        url = url
    )

    fun WeatherResult.toWeather() = Weather(
        airQuality = Weather.AirQuality(
            co = airQuality?.co,
            gbDefraIndex = airQuality?.gbDefraIndex,
            no2 = airQuality?.no2,
            o3 = airQuality?.o3,
            pm10 = airQuality?.pm10,
            pm25 = airQuality?.pm25,
            so2 = airQuality?.so2,
            usEpaIndex = airQuality?.usEpaIndex
        ),
        cloud = cloud,
        condition = Weather.Condition(
            code = condition?.code,
            icon = condition?.icon,
            text = condition?.text
        ),
        feelslikeC = feelslikeC,
        feelslikeF = feelslikeF,
        gustKph = gustKph,
        gustMph = gustMph,
        humidity = humidity,
        isDay = isDay,
        lastUpdated = lastUpdated,
        lastUpdatedEpoch = lastUpdatedEpoch,
        precipIn = precipIn,
        precipMm = precipMm,
        pressureIn = pressureIn,
        pressureMb = pressureMb,
        tempC = tempC,
        tempF = tempF,
        uv = uv,
        visKm = visKm,
        visMiles = visMiles,
        windDegree = windDegree,
        windDir = windDir,
        windKph = windKph,
        windMph = windMph
    )

    fun CurrentLocationWeatherResult.toCurrentLocationWeather() = CurrentLocationWeather(
        location = location.toLocation(),
        current = current.toWeather()
    )
}