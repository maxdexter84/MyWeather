package ru.maxdexter.myweather.util

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import ru.maxdexter.myweather.databinding.FragmentCurrentWeatherBinding
import ru.maxdexter.myweather.databinding.TomorrowFragmentBinding
import ru.maxdexter.myweather.databinding.WeatherCardItemBinding
import ru.maxdexter.myweather.util.extension.bindImage
import ru.maxdexter.myweather.util.extension.currentDate
import ru.maxdexter.myweather.data.model.Daily
import ru.maxdexter.myweather.data.model.WeatherData
import java.util.*


@SuppressLint("SetTextI18n")
fun <T>setData(binding: ViewDataBinding, weatherData: T) {
    when(binding) {
        is FragmentCurrentWeatherBinding -> {
            weatherData as WeatherData
            binding.dateTimeCurrentTextViewId.text =
                Date().currentDate(weatherData.current.dt.toLong())
            binding.currentTempTextViewId.text = "${weatherData.current.temp.toInt().toString()}℃ "
            binding.dayNightTextViewId.text =
                "Ночь:${weatherData.daily[0].temp.night.toInt()}℃ День:${weatherData.daily[0].temp.day.toInt()}℃"
            binding.feelingTempTextViewId.text =
                "Ощущается как: ${weatherData.current.feelsLike.toInt()}℃"
            binding.weatherDescriptionTextViewId.text = weatherData.current.weather[0].description
            bindImage(binding.imageWeatherImageId, weatherData.current.weather[0].icon)
        }
        is TomorrowFragmentBinding -> {
            weatherData as WeatherData
            binding.dateTimeCurrentTextViewId.text = Date().currentDate(weatherData.daily[1].dt)
            binding.currentTempTextViewId.text =
                "от ${weatherData.daily[1].temp.min.toInt()}℃ до${weatherData.daily[1].temp.max.toInt()}℃"
            binding.dayNightTextViewId.text =
                "Ночь:${weatherData.daily[1].temp.night.toInt()}℃ День:${weatherData.daily[1].temp.day.toInt()}℃"
            binding.feelingTempTextViewId.text =
                "днем ощущается как: ${weatherData.daily[1].feelsLike.day.toInt()}℃ ночью ощущается как: ${weatherData.daily[1].feelsLike.night.toInt()}℃"
            binding.weatherDescriptionTextViewId.text = weatherData.daily[1].weather[0].description
            bindImage(binding.imageWeatherImageId, weatherData.daily[0].weather[0].icon)
        }
        is WeatherCardItemBinding -> {
            weatherData as Daily
            binding.feelingTitleId.text = "Ощущается как: ${weatherData.feelsLike.day.toInt()}℃"
            binding.humidityTitleId.text = "${weatherData.humidity}%"
            binding.windTitleId.text = "${weatherData.windSpeed.toInt()}м/с"
            binding.pressureTitleId.text = "${weatherData.pressure.toInt()}мм"
            binding.titleMaxId.text = "${weatherData.temp.max.toInt()}℃"
            binding.titleMinId.text = "${weatherData.temp.min.toInt()}℃"
            binding.itemCurrentTempTextViewId.text = "${weatherData.temp.day.toInt()}℃"
            binding.itemWeatherDescriptionTextViewId.text = weatherData.weather[0].description
            binding.itemDateTimeCurrentTextViewId.text = Date().currentDate(weatherData.dt)
            bindImage(binding.itemImageWeatherImageId,weatherData.weather[0].icon)
        }
    }

}

