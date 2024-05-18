package com.example.testtaskheartrate.ui.medical_recomendations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.data.MedicalCardsRepository
import com.example.testtaskheartrate.data.model.MedicalCard
import com.example.testtaskheartrate.ui.Screen
import com.example.testtaskheartrate.ui.utils.AppBackground


@Composable
fun MedicalRecommendationsScreen(
    onBackScreen: () -> Unit,
    medicalCardsRepository: MedicalCardsRepository,
    goToDetailsScreen: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { Text("") },
                actions = {
                    IconButton(onClick = { }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "More options",
                                modifier = Modifier.clickable { onBackScreen() },
                                tint = Color.White
                            )
                            Text(
                                text = "Медичні рекомендації",
                                modifier = Modifier.padding(horizontal = 5.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        AppBackground {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val medicalCards = medicalCardsRepository.getMedicalCards()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(end = 10.dp)
                ) {
                    medicalCards.forEach { it ->
                        RecordCard(
                            id = it.id,
                            imageRes = it.imageRes,
                            title = it.titleName,
                            description = it.description,
                            onClick = { goToDetailsScreen(it.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordCard(id: Int, imageRes: Int, title: String, description: Int, onClick: (MedicalCard) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .clickable {
                onClick(MedicalCard(id, imageRes, title, description))
            },

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart,
                modifier = Modifier.size(150.dp),
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                maxLines = 4,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewHistoryScreen() {
    val medicalCardsRepository = MedicalCardsRepository()
    MedicalRecommendationsScreen({},medicalCardsRepository, {})
}
