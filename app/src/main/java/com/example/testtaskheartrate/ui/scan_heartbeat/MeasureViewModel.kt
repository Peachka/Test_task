package com.example.testtaskheartrate.ui.scan_heartbeat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskheartrate.data.model.BPMHistory
import com.example.testtaskheartrate.data.BPMHistoryRepository
import com.example.testtaskheartrate.ui.utils.GetCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasureViewModel @Inject constructor(
    private val getCurrentTime: GetCurrentTime,
    private val bpmHistoryRepository: BPMHistoryRepository
): ViewModel() {
    private val _dateTime = MutableStateFlow(getCurrentTime.getCurrentDateTime())
    val dateTime: StateFlow<String> = _dateTime

    init {
        startDateTimeUpdates()
    }

    private fun startDateTimeUpdates() {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Update every second
                _dateTime.value = getCurrentTime.getCurrentDateTime()
            }
        }
    }


    fun addRecord(record: BPMHistory){ viewModelScope.launch {
        bpmHistoryRepository.addRecord(record)
    }
    }
}