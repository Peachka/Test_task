package com.example.testtaskheartrate.ui.onboarding_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.testtaskheartrate.ui.loading_screen.LoadingScreen

@Composable
fun DotsIndicator(
    totalDots: Int,
    currentDotIndex: Int,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.secondary.copy(0.5f)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentDotIndex) 24.dp else 16.dp, 16.dp) // Change size for selected dot
                    .clip(if (index == currentDotIndex) RoundedCornerShape(8.dp) else CircleShape) // Change shape for selected dot
                    .background(
                        if (index == currentDotIndex) selectedColor else unselectedColor
                    )
            )
        }
    }
}





