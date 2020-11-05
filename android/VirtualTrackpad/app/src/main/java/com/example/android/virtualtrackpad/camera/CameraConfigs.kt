package com.example.android.virtualtrackpad.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview

data class CameraConfigs(
    val imageAnalysis: ImageAnalysis,
    val preview: Preview?
)