package ru.maxdexter.myweather.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.databinding.ActivityMainBinding
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.ui.fragments.viewpagerfragment.ViewPagerFragment
import ru.maxdexter.myweather.util.PERMISSION_REQUEST_CODE

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
   private lateinit var locationManager: LocationManager
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)



        locationManager  = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        requestLocationPermissions()

        initSearch()
        loadHistory()

    }

    override fun onPostResume() {
        super.onPostResume()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()
        weatherDataObserve(navController)
    }

    private fun loadHistory() {
        viewModel.getAllHistory(this).observe(this, { history ->
            val list = mutableListOf<String>()
            history.forEach { list.add(it.name) }
            binding.etSearch.setAdapter(
                ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    list
                )
            )

        })
    }

    private fun initSearch() {
        viewModel.placeName.observe(this, {
            it?.let {
                binding.etSearch.apply {
                    binding.etSearch.text.clear()
                    hint = it
                }
            }
        })

        binding.ibSearch.setOnClickListener {
            val s = binding.etSearch.text
            if (s != null) {
                if (s.length > 2) {
                    showProgressBar()
                    viewModel.searchPlace(s, baseContext)
                    hideProgressBar()

                }
            }
        }
    }


    private fun weatherDataObserve(navController: NavController) {
        viewModel.weatherData.observe(this, { weather ->
            when (weather) {
                is LoadData.Success -> {
                    hideProgressBar()
                    if (weather.data != null)
                        navController.navigate(
                            MainActivityDirections.actionGlobalViewPagerFragment(
                                weather.data
                            )
                        )
                }
                is LoadData.Error -> {
                    hideProgressBar()
                    Snackbar.make(binding.root, weather.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                    Log.i("INF", weather.message.toString())
                }
                is LoadData.Loading -> showProgressBar()
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.progressCircular.visibility = View.VISIBLE
    }







    // Запрашиваем Permission’ы для геолокации
    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                viewModel.requestLocation(this, locationManager)
            }
        }
    }


}