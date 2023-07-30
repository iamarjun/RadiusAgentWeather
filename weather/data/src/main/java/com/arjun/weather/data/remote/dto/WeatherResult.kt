package com.arjun.weather.data.remote.dto


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class WeatherResult(
    @Json(name = "air_quality")
    val airQuality: AirQuality?,
    @Json(name = "cloud")
    val cloud: Int?,
    @Json(name = "condition")
    val condition: Condition?,
    @Json(name = "feelslike_c")
    val feelslikeC: Double?,
    @Json(name = "feelslike_f")
    val feelslikeF: Double?,
    @Json(name = "gust_kph")
    val gustKph: Double?,
    @Json(name = "gust_mph")
    val gustMph: Double?,
    @Json(name = "humidity")
    val humidity: Int?,
    @Json(name = "is_day")
    val isDay: Int?,
    @Json(name = "last_updated")
    val lastUpdated: String?,
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Int?,
    @Json(name = "precip_in")
    val precipIn: Double?,
    @Json(name = "precip_mm")
    val precipMm: Double?,
    @Json(name = "pressure_in")
    val pressureIn: Double?,
    @Json(name = "pressure_mb")
    val pressureMb: Double?,
    @Json(name = "temp_c")
    val tempC: Double?,
    @Json(name = "temp_f")
    val tempF: Double?,
    @Json(name = "uv")
    val uv: Double?,
    @Json(name = "vis_km")
    val visKm: Double?,
    @Json(name = "vis_miles")
    val visMiles: Double?,
    @Json(name = "wind_degree")
    val windDegree: Int?,
    @Json(name = "wind_dir")
    val windDir: String?,
    @Json(name = "wind_kph")
    val windKph: Double?,
    @Json(name = "wind_mph")
    val windMph: Double?
) : Parcelable {
    @Keep
    @Parcelize
    data class AirQuality(
        @Json(name = "co")
        val co: Double?,
        @Json(name = "gb-defra-index")
        val gbDefraIndex: Int?,
        @Json(name = "no2")
        val no2: Double?,
        @Json(name = "o3")
        val o3: Double?,
        @Json(name = "pm10")
        val pm10: Double?,
        @Json(name = "pm2_5")
        val pm25: Double?,
        @Json(name = "so2")
        val so2: Double?,
        @Json(name = "us-epa-index")
        val usEpaIndex: Int?
    ) : Parcelable

    @Keep
    @Parcelize
    data class Condition(
        @Json(name = "code")
        val code: Int?,
        @Json(name = "icon")
        val icon: String?,
        @Json(name = "text")
        val text: String?
    ) : Parcelable
}