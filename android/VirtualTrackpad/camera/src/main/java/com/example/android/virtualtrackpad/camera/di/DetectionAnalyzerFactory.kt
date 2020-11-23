package com.example.android.virtualtrackpad.camera.di

import com.example.android.virtualtrackpad.camera.usecase.ObjectDetectorAnalyzer
import com.example.android.virtualtrackpad.core.DetectionConfigs

interface DetectionAnalyzerFactory {

    fun createDetectionAnalyzer(detectionConfigs: DetectionConfigs): ObjectDetectorAnalyzer
}