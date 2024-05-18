package com.example.testtaskheartrate.ui.utils

// CameraUtils.kt
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat


class CameraUtils(
    private val context: Context,
    private val componentActivity: ComponentActivity
) {
    private val cameraPermissionRequest = componentActivity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onCameraPermissionGranted()
        } else {
            onCameraPermissionDenied()
        }
    }

    private var onCameraPermissionGranted: () -> Unit = {}
    private var onCameraPermissionDenied: () -> Unit = {}


    fun requestCameraPermission(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onCameraPermissionGranted = onGranted
        onCameraPermissionDenied = onDenied

        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                onCameraPermissionGranted()
            }
            showRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionRationale()
            }
            else -> {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showRequestPermissionRationale(permission: String): Boolean {
        return false
    }

    private fun showPermissionRationale() {
    }
}

@Composable
fun rememberCameraUtils(
    context: Context,
    componentActivity: ComponentActivity
): CameraUtils {
    return remember { CameraUtils(context, componentActivity) }
}