package com.arjun.weather.domain.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Weather(
    val airQuality: AirQuality?,
    val cloud: Int?,
    val condition: Condition?,
    val feelslikeC: Double?,
    val feelslikeF: Double?,
    val gustKph: Double?,
    val gustMph: Double?,
    val humidity: Int?,
    val isDay: Int?,
    val lastUpdated: String?,
    val lastUpdatedEpoch: Int?,
    val precipIn: Double?,
    val precipMm: Double?,
    val pressureIn: Double?,
    val pressureMb: Double?,
    val tempC: Double?,
    val tempF: Double?,
    val uv: Double?,
    val visKm: Double?,
    val visMiles: Double?,
    val windDegree: Int?,
    val windDir: String?,
    val windKph: Double?,
    val windMph: Double?
) : Parcelable
