package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Day(
    val airQuality: AirQuality?,
    val avghumidity: Int?,
    val avgtempC: Double?,
    val avgtempF: Double?,
    val avgvisKm: Double?,
    val avgvisMiles: Int?,
    val condition: Condition?,
    val dailyChanceOfRain: Int?,
    val dailyChanceOfSnow: Int?,
    val dailyWillItRain: Int?,
    val dailyWillItSnow: Int?,
    val maxtempC: Double?,
    val maxtempF: Double?,
    val maxwindKph: Double?,
    val maxwindMph: Double?,
    val mintempC: Double?,
    val mintempF: Double?,
    val totalprecipIn: Double?,
    val totalprecipMm: Double?,
    val totalsnowCm: Int?,
    val uv: Int?
) : Parcelable

       