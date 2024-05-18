package com.example.testtaskheartrate.ui.history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.example.testtaskheartrate.data.BPMHistoryRepository
import com.example.testtaskheartrate.data.model.BPMHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryListItem(
    val id: Long,
    val heartRate: Int,
    val timeDate: String
)

data class HistoryScreenUiState(
    val historyListItems: List<HistoryListItem> = emptyList(),
    val deleteAllRecords: () -> Unit = {}
)

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val bpmHistoryRepository: BPMHistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryScreenUiState())
    val uiState: StateFlow<HistoryScreenUiState> = _uiState.asStateFlow()

    init {
        observeRecords()
    }

    private fun observeRecords() {
        viewModelScope.launch {
            bpmHistoryRepository.observeRecords()
                .map { records ->
                    val historyListItems = records.map { record ->
                        HistoryListItem(
                            id = record.id,
                            heartRate = record.heartRate,
                            timeDate = record.timeDate
                        )
                    }
                    HistoryScreenUiState(
                        historyListItems = historyListItems,
                        deleteAllRecords = { deleteAllRecords(records) }
                    )
                }
                .collect { _uiState.value = it }
        }
    }

    private fun deleteAllRecords(records: List<BPMHistory>) {
        viewModelScope.launch {
            bpmHistoryRepository.deleteAllRecords(records)
        }
    }
}