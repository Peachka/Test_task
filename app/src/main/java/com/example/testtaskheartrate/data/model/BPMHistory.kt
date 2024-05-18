package com.example.testtaskheartrate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BPMHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "heart_rate")
    val heartRate: Int,
    @ColumnInfo(name = "time_date")
    val timeDate: String
)
