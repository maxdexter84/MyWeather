package ru.maxdexter.myweather.ui.fragments.tendays


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.adapters.WeatherRecyclerAdapter
import ru.maxdexter.myweather.databinding.FragmentTenDaysWeatherBinding
import ru.maxdexter.myweather.model.WeatherData

class TenDaysWeatherFragment : Fragment() {

    companion object {
        fun newInstance(weatherData: WeatherData): TenDaysWeatherFragment {
            val args = Bundle()
            args.putParcelable("WEATHER_DATA",weatherData)
            val fragment = TenDaysWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: TenDaysWeatherViewModel
    private lateinit var binding: FragmentTenDaysWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ten_days_weather,container, false)
        viewModel = ViewModelProvider(this).get(TenDaysWeatherViewModel::class.java)
        val args = requireArguments().getParcelable<WeatherData>("WEATHER_DATA")

        val weatherAdapter =  WeatherRecyclerAdapter(args?.daily)
        binding.recyclerViewTenDaysFragmentId.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTenDaysFragmentId.adapter = weatherAdapter


        return binding.root
    }


}