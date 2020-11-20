package ru.maxdexter.myweather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.maxdexter.myweather.model.Current
import ru.maxdexter.myweather.model.Weather
import ru.maxdexter.myweather.ui.fragments.currentwearher.CurrentWeatherFragment
import ru.maxdexter.myweather.ui.fragments.tomorrow.TomorrowFragment
import ru.maxdexter.myweather.ui.fragments.viewpagerfragment.ViewPagerFragmentDirections

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val weather: Current) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> CurrentWeatherFragment.newInstance(weather)
        else -> TomorrowFragment.newInstance()
    }

}