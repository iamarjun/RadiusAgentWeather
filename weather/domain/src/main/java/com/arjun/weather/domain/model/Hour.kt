package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep

import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Hour(
    val condition: Condition?,
    val tempC: Double?,
    val time: String?,
) : Parcelable