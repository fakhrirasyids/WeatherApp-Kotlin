package com.fakhrirasyids.weatherapp.ui.main

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @field:SerializedName("timezone")
    val timezone: Int,

    @field:SerializedName("weather")
    val weather: List<WeatherItem>,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("cod")
    val cod: Int,

    @field:SerializedName("dt")
    val dt: Long,

    @field:SerializedName("main")
    val main: Main,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("sys")
    val sys: Sys,

    @field:SerializedName("wind")
    val wind: Wind
)

data class Main(

    @field:SerializedName("temp")
    val temp: Double,

    @field:SerializedName("temp_min")
    val tempMin: Double,

    @field:SerializedName("humidity")
    val humidity: Int,

    @field:SerializedName("pressure")
    val pressure: Int,

    @field:SerializedName("feels_like")
    val feelsLike: Double,

    @field:SerializedName("temp_max")
    val tempMax: Double
)

data class Sys(

    @field:SerializedName("country")
    val country: String,

    @field:SerializedName("sunrise")
    val sunrise: Long,

    @field:SerializedName("sunset")
    val sunset: Long,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("type")
    val type: Int
)

data class WeatherItem(

    @field:SerializedName("icon")
    val icon: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("main")
    val main: String
)

data class Wind(

    @field:SerializedName("deg")
    val deg: Int,

    @field:SerializedName("speed")
    val speed: Double
)