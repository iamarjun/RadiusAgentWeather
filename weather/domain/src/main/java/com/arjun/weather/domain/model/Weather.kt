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
) : Parcelable {
    @Keep
    @Parcelize
    data class AirQuality(
        val co: Double?,
        val gbDefraIndex: Int?,
        val no2: Double?,
        val o3: Double?,
        val pm10: Double?,
        val pm25: Double?,
        val so2: Double?,
        val usEpaIndex: Int?
    ) : Parcelable

    @Keep
    @Parcelize
    data class Condition(
        val code: Int?,
        val icon: String?,
        val text: String?
    ) : Parcelable
}