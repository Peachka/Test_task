package com.example.testtaskheartrate.ui.scan_heartbeat.utils

import java.util.Calendar
import java.text.SimpleDateFormat

fun getCurrentDateTime(): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("HH:mm\ndd/MMM/YYYY")
    return formatter.format(calendar.time)
}