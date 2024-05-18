package com.example.testtaskheartrate.data

import com.example.testtaskheartrate.data.model.BPMHistory
import kotlinx.coroutines.flow.Flow

interface BPMHistoryRepository {
    fun observeRecords(): Flow<List<BPMHistory>>
    suspend fun addRecord(record: BPMHistory)
    suspend fun deleteAllRecords(records: List<BPMHistory>)
}