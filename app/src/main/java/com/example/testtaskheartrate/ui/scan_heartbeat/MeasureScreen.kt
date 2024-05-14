package com.example.testtaskheartrate.ui.scan_heartbeat

import android.content.Context
import android.util.Log

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LifecycleOwner
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.ui.scan_heartbeat.utils.HeartBeatDetector
import com.example.testtaskheartrate.ui.scan_heartbeat.utils.getCurrentDateTime

@Composable
fun MeasureScreen(onResult: (Int) -> Unit) {
    val heartBeatDetector = remember { HeartBeatDetector() }
    var heartRate by remember { mutableStateOf(-1) }
    var progress by remember { mutableStateOf(0f) }
    var isMeasuring by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(isMeasuring) {
        if (isMeasuring) {
            progress = 0f
            while (progress < 1f) {
                delay(100)
                progress += 0.01f
            }
            heartRate = heartBeatDetector.getHeartRate()
            isMeasuring = false
            if(heartRate > 0) {
                onResult(heartRate)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isMeasuring) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                CameraPreview(heartBeatDetector)
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "Визначаємо ваш пульс. Утримуйте",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.heart_loading_img),
                        contentDescription = "Loading Image",
                        modifier = Modifier
                            .size(200.dp)
                            .scale(scale)

                    )
                    Text(
                        text = if (heartRate == -1) "--\nbpm" else "${heartBeatDetector.getHeartRate()}\nbpm",
                        style = MaterialTheme.typography.h4
                    )
                }

                Spacer(modifier = Modifier.height(116.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .padding(top = 62.dp)
                        .width(350.dp)
                        .height(50.dp),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                )
            }

        } else {

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {

                Text(
                    text = "Виконай своє перше вимірювання!",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(16.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.heart_loading_img),
                    contentDescription = "Onboarding image 1",
                    modifier = Modifier
                        .size(250.dp)
                )

                Spacer(modifier = Modifier.height(200.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_measure_button),
                    contentDescription = "Onboarding image 1",
                    modifier = Modifier
                        .size(200.dp)
                        .clickable {isMeasuring = true  }
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
@Composable
fun CameraPreview(heartBeatDetector: HeartBeatDetector) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = Executors.newSingleThreadExecutor()
    var cameraProvider: ProcessCameraProvider? = null
    AndroidView(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        factory = { ctx ->
            val view = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                startCameraPreview(context, lifecycleOwner, view, cameraProvider!!, heartBeatDetector )
            }, ContextCompat.getMainExecutor(ctx))

            view
        }

    )
    DisposableEffect(lifecycleOwner) {
        onDispose {
            cameraProvider?.unbindAll()
            executor.shutdown()
        }
    }
}

private fun startCameraPreview(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    view: PreviewView,
    cameraProvider: ProcessCameraProvider,
    heartBeatDetector: HeartBeatDetector
) {

    val cameraExecutor = Executors.newSingleThreadExecutor()
    val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(view.surfaceProvider)
    }

    val imageAnalysis = ImageAnalysis.Builder()
        .build()
        .also {
            it.setAnalyzer(cameraExecutor) { image ->
                val buffer = image.planes[0].buffer
                heartBeatDetector.addFrame(buffer, image.width, image.height)
                image.close()
            }
        }

    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview,
        imageAnalysis
    )
}
