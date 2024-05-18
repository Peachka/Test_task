package com.example.testtaskheartrate.ui.home_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.R
import androidx.compose.ui.platform.LocalContext
import com.example.testtaskheartrate.ui.utils.AppBackground
import com.example.testtaskheartrate.ui.utils.CameraUtils

@Composable
fun HomePage(
    startMeasure: () -> Unit,
    goToIndicatorScreen: () -> Unit,
    goToHistoryScreen: () -> Unit,
    goToMedicalRecomendations: () -> Unit,
    cameraUtils: CameraUtils
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { Text("") },
                actions = {
                    IconButton(onClick = { goToHistoryScreen() }) {
                        Row() {
                            Text(
                                text = "Історія",
                                modifier = Modifier.padding(horizontal = 5.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                            Icon(
                                painterResource(id = R.drawable.time_machine),
                                contentDescription = "More options",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    Text(
                        text = "Виконай своє перше вимірювання!",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.heart_loading_img),
                        contentDescription = "Onboarding image 1",
                        modifier = Modifier
                            .size(250.dp)
                    )

                    Spacer(modifier = Modifier.height(200.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_indicator_button),
                            contentDescription = "Onboarding image 1",
                            modifier = Modifier
                                .size(150.dp)
                                .weight(0.7f)
                                .clickable { goToIndicatorScreen() }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_measure_button),
                            contentDescription = "Onboarding image 1",
                            modifier = Modifier
                                .size(150.dp)
                                .weight(1f)
                                .clickable {
                                    cameraUtils.requestCameraPermission(
                                        onGranted = startMeasure,
                                        onDenied = { /* У випадку відмови */ }
                                    )
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_articel_button),
                            contentDescription = "Onboarding image 1",
                            modifier = Modifier
                                .size(150.dp)
                                .weight(0.7f)
                                .clickable { goToMedicalRecomendations() }
                        )
                    }
                }
            }
        }
    }
}

