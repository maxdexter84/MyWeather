package ru.maxdexter.myweather.ui.fragments.currentwearher

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.model.Current
import ru.maxdexter.myweather.ui.fragments.TenDaysWeatherFragment

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    companion object {
        fun newInstance(current: Current): CurrentWeatherFragment {
            val args = Bundle()
            args.putSerializable("CURRENT_WEATHER",current)
            val fragment = CurrentWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}