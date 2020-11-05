package com.example.android.virtualtrackpad.detection

import com.example.android.virtualtrackpad.camera.ObjectDetectorAnalyzer
import com.example.android.virtualtrackpad.core.DetectionConfigs

interface DetectionAnalyzerFactory {

    fun createDetectionAnalyzer(detectionConfigs: DetectionConfigs): ObjectDetectorAnalyzer
}