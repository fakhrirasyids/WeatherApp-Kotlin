package com.fakhrirasyids.weatherapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fakhrirasyids.weatherapp.databinding.ActivityMainBinding
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSearchView()
        observeLoading()
        observeFailedLoad()
        observeWeatherDetail()
    }

    private fun observeFailedLoad() {
        mainViewModel.isFailedToLoad.observe(this) {
            showErrorMessage(it)
        }
    }

    private fun observeErrorToast() {
        mainViewModel.errorToast.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                DynamicToast.makeError(this, message).show()
            }
        }
    }

    private fun observeLoading() {
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun observeWeatherDetail() {
        mainViewModel.weatherDetail.observe(this) {
            setWeatherDetail(it)
        }
    }

    private fun setWeatherDetail(weatherDetail: WeatherResponse) {
        dummyWelcome(false)
        showWeatherInfoLayout(true)
        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${weatherDetail.weather[0].icon}@2x.png")
            .circleCrop()
            .into(binding.imageStatus)
        binding.apply {
            address.text = StringBuilder("${weatherDetail.name}, ${weatherDetail.sys.country}")
            updatedAt.text = StringBuilder(
                "Updated at: ${
                    SimpleDateFormat(
                        "dd/MM/yyyy hh:mm a",
                        Locale.ENGLISH
                    ).format(Date(weatherDetail.dt * 1000))
                }"
            )
            status.text = weatherDetail.weather[0].description
            temp.text = StringBuilder("${weatherDetail.main.temp}°C")
            tempMin.text = StringBuilder("Min Temp: ${weatherDetail.main.tempMin}°C")
            tempMax.text = StringBuilder("Max Temp: ${weatherDetail.main.tempMax}°C")
            sunrise.text = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(weatherDetail.sys.sunrise * 1000))
            sunset.text = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(weatherDetail.sys.sunset * 1000))
            wind.text = StringBuilder("${weatherDetail.wind.speed}")
            pressure.text = StringBuilder("${weatherDetail.main.pressure}")
            humidity.text = StringBuilder("${weatherDetail.main.humidity}")
            info.text = weatherDetail.weather[0].main
        }
    }

    private fun setUpSearchView() {
        dummyWelcome(true)
        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.getWeatherDetail(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun dummyWelcome(isEmpty: Boolean) {
        binding.dummyWelcome.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showWeatherInfoLayout(isSubmitted: Boolean) {
        binding.mainContainer.visibility = if (isSubmitted) View.VISIBLE else View.GONE
    }

    private fun showErrorMessage(isFailed: Boolean) {
        if (isFailed) {
            observeErrorToast()
        }
    }
}