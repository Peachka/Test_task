package com.example.testtaskheartrate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testtaskheartrate.data.model.BPMHistory

@Database(entities = [BPMHistory::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bpmHistoryDAO(): BPMHistoryDAO

}