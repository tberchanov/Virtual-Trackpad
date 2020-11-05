package com.example.android.virtualtrackpad.core

data class DetectionConfigs(
    val minimumConfidence: Float,
    val numDetection: Int,
    val inputSize: Int,
    val isQuantized: Boolean,
    val modelFile: String,
    val labelsFile: String = "",
    val multipleDetectionsEnabled: Boolean,
    val previewEnabled: Boolean
)