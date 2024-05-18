package com.example.testtaskheartrate.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testtaskheartrate.data.model.BPMHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface BPMHistoryDAO {
    @Query("SELECT * FROM bpmhistory")
    fun observeAll(): Flow<List<BPMHistory>>

    @Insert
    fun insert(record: BPMHistory)

    @Delete
    fun deleteAllEvents(records: List<BPMHistory>)

}