package ru.maxdexter.myweather.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.maxdexter.myweather.data.model.WeatherData
import ru.maxdexter.myweather.ui.fragments.currentwearher.CurrentWeatherFragment
import ru.maxdexter.myweather.ui.fragments.tendays.TenDaysWeatherFragment
import ru.maxdexter.myweather.ui.fragments.tomorrow.TomorrowFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,private val weather: WeatherData) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> CurrentWeatherFragment.newInstance(weather)
        1-> TomorrowFragment.newInstance(weather)
        else -> TenDaysWeatherFragment.newInstance(weather)
    }

}