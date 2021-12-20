package com.example.ivanov_p3.util

import android.content.Context
import com.example.ivanov_p3.R

class Utils {
    fun dateWithMonthName(context: Context, date: String): String {
        val monthName =  context.resources.getStringArray(R.array.month_name_array)
        var currentMonth = date.replaceRange(0,3, "")
        currentMonth = currentMonth.replaceRange(2,8, "")
        val month = monthName[currentMonth.toInt() - 1]

        return date.replaceRange(2, 5, " $month")
    }
}