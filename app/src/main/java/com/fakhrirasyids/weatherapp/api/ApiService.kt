package com.fakhrirasyids.weatherapp.api

import com.fakhrirasyids.weatherapp.BuildConfig
import com.fakhrirasyids.weatherapp.ui.main.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?appid=$API_TOKEN&units=metric")
    fun getWeatherData(
        @Query("q") city: String
    ): Call<WeatherResponse>

    companion object {
        private const val API_TOKEN = BuildConfig.KEY
    }
}