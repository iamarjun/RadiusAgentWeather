package com.arjun.weather.domain.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Alerts(
    val alert: List<String?>?
) : Parcelable
