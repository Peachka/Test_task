package com.example.testtaskheartrate.ui.utils

// CameraUtils.kt
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat


class CameraUtils(
    private val context: Context,
    private val componentActivity: ComponentActivity
) {
    private val cameraPermissionRequest = componentActivity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Handle the permission result
        if (isGranted) {
            // Permission granted, you can proceed with camera-related operations
            onCameraPermissionGranted()
        } else {
            // Permission denied, handle accordingly
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
                // Permission granted, you can proceed with camera-related operations
                onCameraPermissionGranted()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // Show an explanation to the user for requesting the permission
                showPermissionRationale()
            }
            else -> {
                // Request the camera permission
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        // Implement the logic for showing a permission rationale if needed
        return false
    }

    private fun showPermissionRationale() {
        // Implement the logic for showing a permission rationale if needed
    }
}

@Composable
fun rememberCameraUtils(
    context: Context,
    componentActivity: ComponentActivity
): CameraUtils {
    return remember { CameraUtils(context, componentActivity) }
}