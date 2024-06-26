package com.example.testtaskheartrate.ui.loading_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.testtaskheartrate.ui.theme.dimens
import kotlinx.coroutines.delay


@Composable
fun LoadingScreen(
    goAfterFirstLoading: () -> Unit,
    goAfterSecondLoading: () -> Unit,
    isOnboardingComplete: Boolean
) {

    var progress by remember { mutableStateOf(0f) }
    var indicator by remember { mutableStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
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
            indicator += 5
        }
        if (isOnboardingComplete) {
            goAfterSecondLoading()
        } else {
            goAfterFirstLoading()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(40.dp))
        Image(
            painter = painterResource(id = R.drawable.heart_loading_img),
            contentDescription = "Loading Image",
            modifier = Modifier
                .size(250.dp)
                .scale(scale)

        )
        Text(
            text = "Heart Rate",
            style = MaterialTheme.typography.headlineLarge
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            androidx.compose.material.LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small2)
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.medium1)
                    .clip(shape = RoundedCornerShape(15.dp)),
                color = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
            )
            Text(
                text = indicator.toString() + "%",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


//@Preview
//@Composable
//fun PreviewLoadingScreen() {
//    LoadingScreen({}, {}, false)
//}
