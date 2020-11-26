package ru.maxdexter.myweather.ui.fragments.viewpagerfragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.adapters.ViewPagerAdapter
import ru.maxdexter.myweather.databinding.FragmentVewPagerBinding


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


        TabLayoutMediator(binding.tabsFragment, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Сегодня"
                1 -> tab.text = "Завтра"
                2 -> tab.text = "10 дней"
            }
        }.attach()

        return binding.root
    }




}