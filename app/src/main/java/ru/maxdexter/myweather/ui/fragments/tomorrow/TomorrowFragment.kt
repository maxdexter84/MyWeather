package ru.maxdexter.myweather.ui.fragments.tomorrow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.databinding.TomorrowFragmentBinding
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.util.setData

class TomorrowFragment : Fragment() {

    companion object {
        fun newInstance(weather: WeatherData): TomorrowFragment {
            val args = Bundle()
            args.putParcelable("TOMORROW_WEATHER",weather)
            val fragment = TomorrowFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: TomorrowViewModel
    private lateinit var binding: TomorrowFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TomorrowViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.tomorrow_fragment, container, false)
        setData()
        return binding.root
    }

    private fun setData() {
        val args = requireArguments().getParcelable<WeatherData>("TOMORROW_WEATHER")
        if (args != null) {
            setData(binding, args)
        }
    }


}