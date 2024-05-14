package com.example.testtaskheartrate.ui.loading_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testtaskheartrate.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoadingScreen(
    goAfterFirstLoading: () -> Unit,
    goAfterSecondLoading: () -> Unit,
    isOnboardingComplete: Boolean
) {

    var progress by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        progress = 0f
        while (progress < 1f) {
            delay(100)
            progress += 0.05f
        }
        if (isOnboardingComplete) {
            goAfterSecondLoading()
        } else {
            goAfterFirstLoading()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(40.dp))
        Image(
            painter = painterResource(id = R.drawable.heart_loading_img),
            contentDescription = "Loading Image",
            modifier = Modifier
                .size(300.dp)
                .scale(scale)

        )
        Text(
            text = "Heart Rate",
            fontSize = 75.sp
        )

        androidx.compose.material.LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .padding(top = 32.dp)
                .width(300.dp)
                .height(30.dp),
            color = MaterialTheme.colorScheme.secondary,
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(25.dp))
    }


}

