package com.example.testtaskheartrate.data.model
import kotlinx.serialization.Serializable

@Serializable
data class MedicalCard(
    val id: Int,
    val imageRes: Int,
    val titleName: String,
    val description: Int
)