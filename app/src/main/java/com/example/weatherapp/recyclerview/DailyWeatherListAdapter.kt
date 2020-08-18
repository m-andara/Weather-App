package com.example.weatherapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDailyWeatherBinding
import com.example.weatherapp.models.DailyWeather
import com.example.weatherapp.utils.DateUtils

class DailyWeatherListAdapter: ListAdapter<DailyWeather, DailyWeatherListAdapter.DailyWeatherViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<DailyWeather>() {
            override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather) = true

            override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDailyWeatherBinding.inflate(inflater, parent, false)
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, position)
    }

    class DailyWeatherViewHolder(private val binding: ItemDailyWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: DailyWeather, position: Int) {
            val days = DateUtils().getDays()
            binding.apply {
                time.text = days[position]
                dailyTemp.text = "${item.dayTemp} / ${item.nightTemp} \u2109"
                dailyHumidity.text = "${item.humidity.toString()}%"
                dailyType.setImageResource(item.weatherType.drawable)
            }
        }
    }
}