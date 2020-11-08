package com.example.android.virtualtrackpad.camera.model

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview

internal data class CameraConfigs(
    val imageAnalysis: ImageAnalysis,
    val preview: Preview?
)