package ru.maxdexter.myweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.adapters.ViewPagerAdapter
import ru.maxdexter.myweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        viewModel.weatherData.observe(this, {weather->
            when(weather){
                is LoadData.Success -> {
                    hideProgressBar()
                    if (weather.data != null)
                        navController.navigate(MainActivityDirections.actionGlobalViewPagerFragment(weather.data))

                }
                is  LoadData.Error -> {
                    hideProgressBar()
                    Snackbar.make(binding.root, weather.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is  LoadData.Loading -> showProgressBar()

            }
        })
    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.progressCircular.visibility = View.VISIBLE
    }
}