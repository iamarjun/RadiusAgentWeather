package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Day(
    val condition: Condition?,
    val maxtempC: Double?,
    val mintempC: Double?,
) : Parcelable

       