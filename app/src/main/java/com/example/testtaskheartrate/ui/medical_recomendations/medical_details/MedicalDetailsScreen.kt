package com.example.testtaskheartrate.ui.medical_recomendations.medical_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.data.MedicalCardsRepository
import com.example.testtaskheartrate.data.model.MedicalCard
import com.example.testtaskheartrate.ui.utils.AppBackground


@Composable
fun MedicalDetailsScreen(
    medicalCardsRepository: MedicalCardsRepository,
    id: Int,
    onBackScreen: () -> Unit
) {
    val medicalCard = medicalCardsRepository.getMedicalCardById(id)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { androidx.compose.material3.Text("") },
                actions = {
                    IconButton(onClick = { }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "More options",
                                modifier = Modifier.clickable { onBackScreen()},
                                tint = Color.White
                            )

                            if (medicalCard != null) {
                                androidx.compose.material3.Text(
                                    text = medicalCard.titleName,
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            )
        }
    ) { paddingValues ->

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(top = 5.dp, end = 10.dp) // Space for scrollbar
                ) {
                    if (medicalCard != null) {
                        RecordCard(
                            imageRes = medicalCard.imageRes,
                            description = medicalCard.description
                        )
                    }
                }

        }
    }
}

@Composable
fun RecordCard(imageRes: Int,  description: Int) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp)),

        horizontalAlignment = Alignment.CenterHorizontally,
//        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
                // Crop the image to fill the available space

            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            androidx.compose.material3.Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
//                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview
//@Composable
//fun MedCard(){
//    MedicalDetailsScreen()
//}