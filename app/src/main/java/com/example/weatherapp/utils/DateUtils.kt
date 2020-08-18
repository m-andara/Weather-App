package com.example.weatherapp.utils

import java.time.LocalDate
import java.util.*

class DateUtils {

    private val cal = Calendar.getInstance()
    private val months = listOf<String>(
        "January", "February",
        "March", "April", "May", "June", "July",
        "August", "September", "October", "November",
        "December"
    )

    fun getDateFormatted(): String {

        val currentMonth = months[cal.get(Calendar.MONTH)]
        val currentDay = cal.get(Calendar.DAY_OF_MONTH)
        val currentYear = cal.get(Calendar.YEAR)
        return "$currentMonth $currentDay, $currentYear"
    }

    fun getDays(): List<String> {
        val days = mutableListOf<String>()
        for(x in 0..4) {
            when (cal.get(Calendar.DAY_OF_WEEK) + x) {
                1 -> days.add("Sunday")
                2 -> days.add("Monday")
                3 -> days.add("Tuesday")
                4 -> days.add("Wednesday")
                5 -> days.add("Thursday")
                6 -> days.add("Friday")
                7 -> days.add("Saturday")
                else -> days.add("")
            }
        }
        return days
    }

    fun getHours(): List<String> {
        val hours = mutableListOf<String>()
        val startHour = cal.get(Calendar.HOUR_OF_DAY)
        for(x in 0..4) {
            if((startHour + x) > 23) {
                hours.add(formatTime((startHour + x) - 24))
                continue
            }
            hours.add(formatTime(startHour + x))
        }
        return hours
    }

    private fun formatTime(time: Int): String = when(time) {
        0 -> "12 AM"
        1 -> "1 AM"
        2 -> "2 AM"
        3 -> "3 AM"
        4 -> "4 AM"
        5 -> "5 AM"
        6 -> "6 AM"
        7 -> "7 AM"
        8 -> "8 AM"
        9 -> "9 AM"
        10 -> "10 AM"
        11 -> "11 AM"
        12 -> "12 PM"
        13 -> "1 PM"
        14 -> "2 PM"
        15 -> "3 PM"
        16 -> "4 PM"
        17 -> "5 PM"
        18 -> "6 PM"
        19 -> "7 PM"
        20 -> "8 PM"
        21 -> "9 PM"
        22 -> "10 PM"
        23 -> "11 PM"
        else -> ""
    }
}