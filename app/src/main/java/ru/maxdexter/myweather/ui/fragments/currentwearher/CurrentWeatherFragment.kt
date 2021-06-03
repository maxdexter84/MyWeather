package ru.maxdexter.myweather.ui.fragments.currentwearher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.databinding.FragmentCurrentWeatherBinding
import ru.maxdexter.myweather.data.model.WeatherData
import ru.maxdexter.myweather.util.setData

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    companion object {
        fun newInstance(weather: WeatherData): CurrentWeatherFragment {
            val args = Bundle()
            args.putParcelable("CURRENT_WEATHER",weather)
            val fragment = CurrentWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
    lateinit var binding: FragmentCurrentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_weather, container, false)
        setData()

        return binding.root
    }



    private fun setData() {
        val args = requireArguments().getParcelable<WeatherData>("CURRENT_WEATHER")
        if (args != null) {
            setData(binding, args)
        }
    }

}