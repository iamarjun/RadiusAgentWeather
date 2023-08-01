package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DayResult(
    @Json(name = "air_quality")
    val airQuality: AirQualityResult?,
    @Json(name = "avghumidity")
    val avghumidity: Int?,
    @Json(name = "avgtemp_c")
    val avgtempC: Double?,
    @Json(name = "avgtemp_f")
    val avgtempF: Double?,
    @Json(name = "avgvis_km")
    val avgvisKm: Double?,
    @Json(name = "avgvis_miles")
    val avgvisMiles: Int?,
    @Json(name = "condition")
    val condition: ConditionResult?,
    @Json(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int?,
    @Json(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int?,
    @Json(name = "daily_will_it_rain")
    val dailyWillItRain: Int?,
    @Json(name = "daily_will_it_snow")
    val dailyWillItSnow: Int?,
    @Json(name = "maxtemp_c")
    val maxtempC: Double?,
    @Json(name = "maxtemp_f")
    val maxtempF: Double?,
    @Json(name = "maxwind_kph")
    val maxwindKph: Double?,
    @Json(name = "maxwind_mph")
    val maxwindMph: Double?,
    @Json(name = "mintemp_c")
    val mintempC: Double?,
    @Json(name = "mintemp_f")
    val mintempF: Double?,
    @Json(name = "totalprecip_in")
    val totalprecipIn: Double?,
    @Json(name = "totalprecip_mm")
    val totalprecipMm: Double?,
    @Json(name = "totalsnow_cm")
    val totalsnowCm: Int?,
    @Json(name = "uv")
    val uv: Int?
) : Parcelable

       