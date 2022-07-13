package com.fakhrirasyids.weatherapp.api

import com.fakhrirasyids.weatherapp.ui.main.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?appid=5817daeb5396449487a2be7c438118f0&units=metric")
    fun getWeatherData(
        @Query("q") city: String
    ): Call<WeatherResponse>
}