package ru.maxdexter.myweather.ui.fragments.viewpagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.adapters.ViewPagerAdapter
import ru.maxdexter.myweather.databinding.FragmentVewPagerBinding
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.ui.fragments.currentwearher.CurrentWeatherFragment
import ru.maxdexter.myweather.ui.fragments.tomorrow.TomorrowFragment
import ru.maxdexter.myweather.util.Location

class ViewPagerFragment : Fragment() {


    private var adapter: ViewPagerAdapter? = null
    private lateinit var binding: FragmentVewPagerBinding
    private val viewModel: PagerViewModel by lazy {
        ViewModelProvider(this).get(PagerViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vew_pager, container, false)
        val args = arguments?.let { ViewPagerFragmentArgs.fromBundle(it) }
        val viewPagerAdapter = args?.let { ViewPagerAdapter(parentFragmentManager,lifecycle, it.weather) }
        binding.viewPager.adapter = viewPagerAdapter

//        Toast.makeText(context, "PagerFragment", Toast.LENGTH_SHORT).show()
//
//        activity?.let { viewModel.getLocation(it) }
//
//        viewModel.coordinates.observe(viewLifecycleOwner, {coord ->
//            if (coord != null) {
//                Toast.makeText(context, "${coord.first} ${coord.second}", Toast.LENGTH_LONG).show()
//            }
//
//        })

        return binding.root
    }




}