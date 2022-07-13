package com.fakhrirasyids.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhrirasyids.weatherapp.api.ApiConfig
import com.fakhrirasyids.weatherapp.wrapper.Event
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _weatherDetail = MutableLiveData<WeatherResponse>()
    val weatherDetail: LiveData<WeatherResponse> = _weatherDetail

    private val _errorToast = MutableLiveData<Event<String?>>()
    val errorToast: LiveData<Event<String?>> = _errorToast

    private val _isFailedToLoad = MutableLiveData<Boolean>()
    val isFailedToLoad: LiveData<Boolean> = _isFailedToLoad

    fun getWeatherDetail(city: String) {
        _isLoading.value = true
        _isFailedToLoad.value = false
        val client = ApiConfig.getApiService().getWeatherData(city)
        client.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _isFailedToLoad.value = false
                    if (responseBody != null) {
                        _weatherDetail.postValue(responseBody)
                    }
                } else {
                    _isFailedToLoad.value = true
                    val errorMessage = parseError(response)
                    _errorToast.value = Event("$errorMessage!")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _isLoading.value = false
                _isFailedToLoad.value = true
                _errorToast.value = Event("Can't connect to the server!")
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun <T> parseError(response: Response<T>): String? {
        var message: String? = null
        try {
            val jsonObjError = JSONObject(response.errorBody()!!.string())
            message = jsonObjError.getString("message")
            Log.d(TAG, "errorMessage: $message")
            return message
        } catch (e: Exception) {
            Log.d(TAG, "failed retrieving error message: ${e.message}")
        }
        return message
    }

    companion object {
        private const val TAG = "Main View Model"
    }
}


