package com.example.testtaskheartrate.ui.result_screen


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
import androidx.compose.material.Shapes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.ui.scan_heartbeat.utils.getCurrentDateTime

@Composable
fun HeartRateResultScreen(
    heartRate: Int,
    timestamp: String = getCurrentDateTime(),
    goToMeasure: () -> Unit
) {
    val resultColor = when (heartRate) {
        in Int.MIN_VALUE..59 -> MaterialTheme.colorScheme.primary
        in 60..100 -> MaterialTheme.colorScheme.onSecondary
        else -> MaterialTheme.colorScheme.secondary
    }

    val resultText = when (heartRate) {
        in Int.MIN_VALUE..59 -> "Уповільнений - $heartRate"
        in 60..100 -> "Звичайно - $heartRate"
        else -> "Прискорений - $heartRate"
    }
    val shape = androidx.compose.foundation.shape.RoundedCornerShape(10)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Box(
            modifier = Modifier
                .size(height = 150.dp, width = 350.dp)
                .background(MaterialTheme.colorScheme.onPrimary.copy(0.5f), shape = shape),


        ) {
            Column(modifier = Modifier.padding(7.dp)) {

                Text(
                    text = "Ваш Результат",
//            style = MaterialTheme.typography.h5
                )

                Row() {
                    Text(
                        text = resultText,
                        color = resultColor,
//            style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.width(140.dp))
                    Text(
                        text = timestamp,
//            style = MaterialTheme.typography.body1
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "<60 BPM",

                        )
                    Text(
                        text = "60-100 BPM",

                        )
                    Text(
                        text = ">100 BPM",

                        )
                }
            }
        }
        Spacer(modifier = Modifier.height(220.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge, // Or any other desired shape
            onClick = goToMeasure) {
            Text(text = "Готово")

        }
    }
}

@Preview
@Composable
fun Preview() {
    HeartRateResultScreen(81, "12.23 \n14/May/2024", {})
}