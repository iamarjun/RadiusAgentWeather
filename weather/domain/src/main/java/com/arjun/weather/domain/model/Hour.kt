package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep

import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Hour(
    
    val condition: Condition?,
    val feelslikeC: Double?,
    val precipIn: Double?,
    val tempC: Double?,
    val time: String?,
    val uv: Int?,
    val windDir: String?,
    val windKph: Double?,
) : Parcelable