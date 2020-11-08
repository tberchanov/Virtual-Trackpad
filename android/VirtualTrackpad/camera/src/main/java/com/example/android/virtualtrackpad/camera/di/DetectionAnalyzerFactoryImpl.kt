package com.example.android.virtualtrackpad.camera.di

import android.content.Context
import com.example.android.virtualtrackpad.camera.ObjectDetectorAnalyzer
import com.example.android.virtualtrackpad.core.DetectionConfigs

internal class DetectionAnalyzerFactoryImpl(
    private val context: Context
) : DetectionAnalyzerFactory {

    override fun createDetectionAnalyzer(
        detectionConfigs: DetectionConfigs
    ) = ObjectDetectorAnalyzer(context, detectionConfigs)
}