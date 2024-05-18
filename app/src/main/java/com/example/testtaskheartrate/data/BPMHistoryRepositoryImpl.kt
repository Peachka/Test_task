package com.example.testtaskheartrate.data

import com.example.testtaskheartrate.data.model.BPMHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BPMHistoryRepositoryImpl(
    private val bpmHistoryDAO: BPMHistoryDAO
): BPMHistoryRepository {
    override fun observeRecords(): Flow<List<BPMHistory>> {
        return bpmHistoryDAO.observeAll()
    }

    override suspend fun addRecord(record: BPMHistory) {
        withContext(Dispatchers.IO){
            bpmHistoryDAO.insert(record)
        }
    }

    override suspend fun deleteAllRecords(records: List<BPMHistory>) {
        withContext(Dispatchers.IO){
            bpmHistoryDAO.deleteAllEvents(records)
        }
    }

}