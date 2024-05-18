package com.example.testtaskheartrate.data

import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.data.model.MedicalCard

class MedicalCardsRepository {
    private val medicalCards = mutableListOf<MedicalCard>()

    init {
        // Initialize the list with some sample data
        medicalCards.add(
            MedicalCard(
                id = 1,
                imageRes = R.drawable.doctors_image,
                titleName = "Що таке пульс",
                description = R.string.bpm_description
            )
        )
        medicalCards.add(
            MedicalCard(
                id = 2,
                imageRes = R.drawable.products_image,
                titleName = "Здорове харчування",
                description = R.string.health_eating_description
            )
        )
    }

    fun getMedicalCards(): List<MedicalCard> {
        return medicalCards
    }

    fun getMedicalCardById(id: Int): MedicalCard? {
        return medicalCards.find { it.id == id }
    }
}