package ru.maxdexter.myweather.ui.fragments.viewpagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ru.maxdexter.myweather.R

class ViewPagerFragment : Fragment(R.layout.fragment_vew_pager) {




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager2 = view.findViewById(R.id.view_pager) as ViewPager2


    }
}