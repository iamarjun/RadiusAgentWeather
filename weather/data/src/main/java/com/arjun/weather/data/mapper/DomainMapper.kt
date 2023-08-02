package com.arjun.weather.data.mapper

import com.arjun.weather.data.remote.dto.AirQualityResult
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
import com.arjun.weather.domain.model.AirQuality
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
        airQuality = airQuality?.toAirQuality(),
        cloud = cloud,
        condition = condition?.toCondition(),
        feelslikeC = feelslikeC,
        feelslikeF = feelslikeF,
        gustKph = gustKph,
        gustMph = gustMph,
        humidity = humidity,
        isDay = isDay,
        lastUpdated = lastUpdated,
        lastUpdatedEpoch = lastUpdatedEpoch,
        precipIn = precipIn,
        precipMm = precipMm,
        pressureIn = pressureIn,
        pressureMb = pressureMb,
        tempC = tempC,
        tempF = tempF,
        uv = uv,
        visKm = visKm,
        visMiles = visMiles,
        windDegree = windDegree,
        windDir = windDir,
        windKph = windKph,
        windMph = windMph
    )

    fun AirQualityResult.toAirQuality() = AirQuality(
        co = co,
        gbDefraIndex = gbDefraIndex,
        no2 = no2,
        o3 = o3,
        pm10 = pm10,
        pm25 = pm25,
        so2 = so2,
        usEpaIndex = usEpaIndex
    )

    fun AstroResult.toAstro() = Astro(
        isMoonUp = isMoonUp,
        isSunUp = isSunUp,
        moonIllumination = moonIllumination,
        moonPhase = moonPhase,
        moonrise = moonrise,
        moonset = moonset,
        sunrise = sunrise,
        sunset = sunset
    )

    fun ConditionResult.toCondition() = Condition(
        code = code,
        icon = icon,
        text = text
    )

    fun DayResult.toDay() = Day(
        airQuality = airQuality?.toAirQuality(),
        avghumidity = avghumidity,
        avgtempC = avgtempC,
        avgtempF = avgtempF,
        avgvisKm = avgvisKm,
        avgvisMiles = avgvisMiles,
        condition = condition?.toCondition(),
        dailyChanceOfRain = dailyChanceOfRain,
        dailyChanceOfSnow = dailyChanceOfSnow,
        dailyWillItRain = dailyWillItRain,
        dailyWillItSnow = dailyWillItSnow,
        maxtempC = maxtempC,
        maxtempF = maxtempF,
        maxwindKph = maxwindKph,
        maxwindMph = maxwindMph,
        mintempC = mintempC,
        mintempF = mintempF,
        totalprecipIn = totalprecipIn,
        totalprecipMm = totalprecipMm,
        totalsnowCm = totalsnowCm,
        uv = uv
    )

    fun HourResult.toHour() = Hour(
        airQuality = airQuality?.toAirQuality(),
        chanceOfRain = chanceOfRain,
        chanceOfSnow = chanceOfSnow,
        cloud = cloud,
        condition = condition?.toCondition(),
        dewpointC = dewpointC,
        dewpointF = dewpointF,
        feelslikeC = feelslikeC,
        feelslikeF = feelslikeF,
        gustKph = gustKph,
        gustMph = gustMph,
        heatindexC = heatindexC,
        heatindexF = heatindexF,
        humidity = humidity,
        isDay = isDay,
        precipIn = precipIn,
        precipMm = precipMm,
        pressureIn = pressureIn,
        pressureMb = pressureMb,
        tempC = tempC,
        tempF = tempF,
        time = time,
        timeEpoch = timeEpoch,
        uv = uv,
        visKm = visKm,
        visMiles = visMiles,
        willItRain = willItRain,
        willItSnow = willItSnow,
        windDegree = windDegree,
        windDir = windDir,
        windKph = windKph,
        windMph = windMph,
        windchillC = windchillC,
        windchillF = windchillF
    )

    fun ForecastdayResult.toForecastDay() = Forecastday(
        astro = astro?.toAstro(),
        date = date,
        dateEpoch = dateEpoch,
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