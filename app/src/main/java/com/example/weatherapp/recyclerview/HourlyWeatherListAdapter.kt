package com.example.weatherapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDailyWeatherBinding
import com.example.weatherapp.models.DailyWeather
import com.example.weatherapp.models.Weather
import com.example.weatherapp.utils.DateUtils
import java.util.*

class HourlyWeatherListAdapter: ListAdapter<Weather, HourlyWeatherListAdapter.HourlyWeatherViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather) = true

            override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDailyWeatherBinding.inflate(inflater, parent, false)
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, position)
    }

    class HourlyWeatherViewHolder(private val binding: ItemDailyWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Weather, position: Int) {
            val hours = DateUtils().getHours()
            binding.apply {
                time.text = hours[position]
                dailyTemp.text = "${item.temp} \u2109"
                dailyHumidity.text = "${item.humidity.toString()}%"
                dailyType.setImageResource(item.weatherType.drawable)
            }
        }
    }
}