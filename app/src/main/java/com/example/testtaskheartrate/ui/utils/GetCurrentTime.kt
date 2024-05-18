package com.example.testtaskheartrate.ui.utils

import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class GetCurrentTime(){
    fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("HH:mm dd/MM/YYYY", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}
