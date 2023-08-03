package com.arjun.weather.data.mapper

import com.arjun.weather.data.remote.dto.AlertResult
import com.arjun.weather.data.remote.dto.AlertsResult
import com.arjun.weather.data.remote.dto.AstroResult
import com.arjun.weather.data.remote.dto.ConditionResult
import com.arjun.weather.data.remote.dto.CurrentLocationWeatherResult
import com.arjun.weather.data.remote.dto.DayResult
import com.arjun.weather.data.remote.dto.ForecastResult
import com.arjun.weather.data.remote.dto.ForecastdayResult
import com.arjun.weather.data.remote.dto.HourResult
import com.arjun.weather.data.remote.dto.LocationResult
import com.arjun.weather.data.remote.dto.WeatherResult
import com.arjun.weather.domain.model.Alert
import com.arjun.weather.domain.model.Alerts
import com.arjun.weather.domain.model.Astro
import com.arjun.weather.domain.model.Condition
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Day
import com.arjun.weather.domain.model.Forecast
import com.arjun.weather.domain.model.Forecastday
import com.arjun.weather.domain.model.Hour
import com.arjun.weather.domain.model.Location
import com.arjun.weather.domain.model.Weather

object DomainMapper {
    fun LocationResult.toLocation() = Location(
        country = country,
        id = id,
        lat = lat,
        lon = lon,
        name = name,
        region = region,
        url = url
    )

    fun WeatherResult.toWeather() = Weather(
        condition = condition?.toCondition(),
        feelslikeC = feelslikeC,
        precipMm = precipMm,
        uv = uv,
        windDir = windDir,
        windKph = windKph,
    )

    fun AstroResult.toAstro() = Astro(
        sunrise = sunrise,
        sunset = sunset
    )

    fun ConditionResult.toCondition() = Condition(
        icon = icon,
        text = text
    )

    fun DayResult.toDay() = Day(
        condition = condition?.toCondition(),
        maxtempC = maxtempC,
        mintempC = mintempC,
    )

    fun HourResult.toHour() = Hour(
        condition = condition?.toCondition(),
        tempC = tempC,
        time = time,
    )

    fun ForecastdayResult.toForecastDay() = Forecastday(
        astro = astro?.toAstro(),
        date = date,
        day = day?.toDay(),
        hour = hour?.map { it?.toHour() }
    )

    fun ForecastResult.toForecast() =
        Forecast(forecastday = forecastday?.map { it?.toForecastDay() })

    fun AlertResult.toAlert() = Alert(
        areas = areas,
        category = category,
        certainty = certainty,
        desc = desc,
        effective = effective,
        event = event,
        expires = expires,
        headline = headline,
        instruction = instruction,
        msgtype = msgtype,
        note = note,
        severity = severity,
        urgency = urgency
    )

    fun AlertsResult.toAlerts() = Alerts(alert = alerts?.map { it?.toAlert() })

    fun CurrentLocationWeatherResult.toCurrentLocationWeather() = CurrentLocationWeather(
        location = location.toLocation(),
        current = current.toWeather(),
        alerts = alerts?.toAlerts(),
        forecast = forecast?.toForecast()
    )
}