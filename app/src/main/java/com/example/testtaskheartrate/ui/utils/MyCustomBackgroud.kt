package com.example.testtaskheartrate.ui.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.tooling.preview.Preview
import com.example.testtaskheartrate.ui.loading_screen.LoadingScreen

@Composable
fun AppBackground(
    content: @Composable () -> Unit
) {
    val cornerRadius = CornerRadius(1000f, 350f)
    val blueColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.size(1000.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw the white background
            drawRect(
                color = Color.White,
                size = size
            )

            // Draw the blue top area
            drawRoundRect(
                color = blueColor,
                topLeft = Offset(0f, -400f),
                size = Size(canvasWidth, canvasHeight * 1f),
                cornerRadius = cornerRadius
            )


        }
        content()
    }
}

