package com.example.testtaskheartrate.ui.result_screen


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.ui.utils.GetCurrentTime
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.tooling.preview.Preview
import com.example.testtaskheartrate.ui.utils.AppBackground

@Composable
fun HeartRateResultScreen(
    heartRate: Int,
    timestamp: String = GetCurrentTime().getCurrentDateTime(),
    goToHistoryScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    val resultColor = when (heartRate) {
        in Int.MIN_VALUE..59 -> MaterialTheme.colorScheme.primary
        in 60..100 -> MaterialTheme.colorScheme.onSecondary
        else -> MaterialTheme.colorScheme.secondary
    }

    val resultText = when (heartRate) {
        in Int.MIN_VALUE..59 -> "Уповільнений"
        in 60..100 -> "Звичайно"
        else -> "Прискорений"
    }
    val resultBPMText = when (heartRate) {
        in Int.MIN_VALUE..59 -> "<60 BPM"
        in 60..100 -> "60-100 BPM"
        else -> ">100 BPM"
    }

    val shape = RoundedCornerShape(10)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { androidx.compose.material.Text("") },
                actions = {
                    // Add your top app bar actions here
                    Row (modifier = Modifier.fillMaxWidth()
                    ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Результат",
                            color= Color.White)
                        IconButton(onClick = { goToHistoryScreen() }) {
                            Row() {
                                androidx.compose.material.Text(
                                    text = "Історія",
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                                androidx.compose.material.Icon(
                                    painterResource(id = R.drawable.time_machine),
                                    contentDescription = "More options",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                }
            )
        }
    ) { paddingValues ->
        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {


                Box(
                    modifier = Modifier
                        .width( width = 350.dp)
                        .padding(top = 100.dp)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(0.5f), shape = shape)

                    ) {
                    Column(modifier = Modifier.padding(7.dp)) {
                        Text(
                            text = "Ваш Результат",
//            style = MaterialTheme.typography.h5
                        )
                        Row() {
                            Text(
//                        modifier = Modifier.weight(1f),
                                text = "$resultText - $heartRate",
                                color = resultColor,
//            style = MaterialTheme.typography.h4
                            )
                            Spacer(modifier = Modifier.width(140.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier.weight(0.1f),
                                    painter = painterResource(id = R.drawable.ic_time),
                                    contentDescription = "time icon"
                                )
                                Text(
                                    modifier = Modifier.weight(0.5f),
                                    text = "${timestamp.split(" ")[0]} \n ${timestamp.split(" ")[1].trim()}"
//            style = MaterialTheme.typography.body1
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        IndicatorLine()

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            BPMBoxes(
                                bpmDescription = "Уповільнений",
                                bpmRate = "<60 BPM",
                                circleColor = MaterialTheme.colorScheme.primary
                            )
                            BPMBoxes(
                                bpmDescription = "Звичайний",
                                bpmRate = "60-100 BPM",
                                circleColor = MaterialTheme.colorScheme.onSecondary
                            )
                            BPMBoxes(
                                bpmDescription = "Прискорений",
                                bpmRate = ">100 BPM",
                                circleColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.extraLarge, // Or any other desired shape
                    onClick = goToHomeScreen
                ) {
                    Text(text = "Готово")

                }

            }

        }
    }
}

@Composable
private fun IndicatorLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(64.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(256.dp)
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(start = 64.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(128.dp)
                .background(Color.Red)
                .padding(start = 320.dp)
        )
    }
}

@Composable
fun BPMBoxes(bpmDescription: String, bpmRate: String, circleColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                CircleCanvas(
                    modifier = Modifier.size(1.dp),
                    circleColor = circleColor,
                    circleRadius = 15f
                )
                Text(text = bpmDescription,
                    modifier = Modifier.padding(start = 10.dp))

            }

        }
        Text(text = bpmRate)
    }
}

@Composable
fun CircleCanvas(
    modifier: Modifier = Modifier,
    circleColor: Color = Color.Blue,
    circleRadius: Float = 5f
) {
    Canvas(
        modifier = modifier
            .size(5.dp) // Adjust the size of the Canvas as needed
    ) {
        val centerX = size.width / 100
        val centerY = size.height / 100

        drawCircle(
            color = circleColor,
            radius = circleRadius,
            center = Offset(centerX, centerY)
        )
    }
}

@Preview
@Composable
fun PreviewResult() {
    HeartRateResultScreen(81, "12.23 \n14/May/2024", {}, {})
}