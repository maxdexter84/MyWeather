package ru.maxdexter.myweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.myweather.R
import ru.maxdexter.myweather.databinding.WeatherCardItemBinding
import ru.maxdexter.myweather.model.Daily
import ru.maxdexter.myweather.util.setData

class WeatherRecyclerAdapter(private val list: List<Daily>?): RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {



    class WeatherViewHolder(val binding: WeatherCardItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(daily: Daily){
            setData(binding,daily)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = list?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}