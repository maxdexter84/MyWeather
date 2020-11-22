package ru.maxdexter.myweather.util

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import ru.maxdexter.myweather.databinding.FragmentCurrentWeatherBinding
import ru.maxdexter.myweather.databinding.TomorrowFragmentBinding
import ru.maxdexter.myweather.extension.currentDate
import ru.maxdexter.myweather.model.WeatherData
import java.util.*


@SuppressLint("SetTextI18n")
fun setData(binding: ViewDataBinding, weatherData: WeatherData) {
    when(binding) {
        is FragmentCurrentWeatherBinding -> {
            binding.dateTimeCurrentTextViewId.text = Date().currentDate(weatherData.current.dt.toLong())
            binding.currentTempTextViewId.text = weatherData.current.temp.toInt().toString()
            binding.dayNightTextViewId.text = "Ночь:${weatherData.daily[0].temp.night.toInt()}℃ День:${weatherData.daily[0].temp.day.toInt()}℃"
            binding.feelingTempTextViewId.text = "Ощущается как: ${weatherData.current.feelsLike.toInt()}℃"
            binding.weatherDescriptionTextViewId.text = weatherData.current.weather[0].description
        }
        is TomorrowFragmentBinding -> {
            binding.dateTimeCurrentTextViewId.text = Date().currentDate(weatherData.daily[1].dt)
            binding.currentTempTextViewId.text = "от ${weatherData.daily[1].temp.min.toInt()}℃ до${weatherData.daily[1].temp.max.toInt()}℃"
            binding.dayNightTextViewId.text = "Ночь:${weatherData.daily[1].temp.night.toInt()}℃ День:${weatherData.daily[1].temp.day.toInt()}℃"
            binding.feelingTempTextViewId.text = "днем ощущается как: ${weatherData.daily[1].feelsLike.day.toInt()}℃ ночью ощущается как: ${weatherData.daily[1].feelsLike.night.toInt()}℃"
            binding.weatherDescriptionTextViewId.text = weatherData.daily[1].weather[0].description
        }
    }

}