package com.example.testtaskheartrate.ui.medical_recomendations

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testtaskheartrate.data.model.MedicalCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MedicalRecommendationsViewModel @Inject constructor(): ViewModel() {
    private val _selectedMedicalCard = MutableStateFlow<MedicalCard?>(null)
    val selectedMedicalCard: StateFlow<MedicalCard?> = _selectedMedicalCard

}