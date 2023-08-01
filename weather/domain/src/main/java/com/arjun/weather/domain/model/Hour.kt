package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep

import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Hour(
    val airQuality: AirQuality?,
    val chanceOfRain: Int?,
    val chanceOfSnow: Int?,
    val cloud: Int?,
    val condition: Condition?,
    val dewpointC: Double?,
    val dewpointF: Double?,
    val feelslikeC: Double?,
    val feelslikeF: Double?,
    val gustKph: Double?,
    val gustMph: Double?,
    val heatindexC: Double?,
    val heatindexF: Double?,
    val humidity: Int?,
    val isDay: Int?,
    val precipIn: Double?,
    val precipMm: Double?,
    val pressureIn: Double?,
    val pressureMb: Int?,
    val tempC: Double?,
    val tempF: Double?,
    val time: String?,
    val timeEpoch: Int?,
    val uv: Int?,
    val visKm: Double?,
    val visMiles: Int?,
    val willItRain: Int?,
    val willItSnow: Int?,
    val windDegree: Int?,
    val windDir: String?,
    val windKph: Double?,
    val windMph: Double?,
    val windchillC: Double?,
    val windchillF: Double?
) : Parcelable