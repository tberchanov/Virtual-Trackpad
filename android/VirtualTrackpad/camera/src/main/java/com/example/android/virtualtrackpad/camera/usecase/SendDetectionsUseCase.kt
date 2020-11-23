package com.example.android.virtualtrackpad.camera.usecase

import com.example.android.virtualtrackpad.DetectionResult
import com.example.android.virtualtrackpad.data.device.connection.DeviceRepository
import javax.inject.Inject

internal class SendDetectionsUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    fun execute(analyzerResult: ObjectDetectorAnalyzer.Result) {
        if (analyzerResult.objects.isEmpty()) {
            sendNoDetections()
        } else {
            sendDetections(analyzerResult.objects)
        }
    }

    private fun sendNoDetections() {
        deviceRepository.sendData(NO_DETECTIONS)
    }

    private fun sendDetections(detections: List<DetectionResult>) {
        val data = detections.map {
            it.location
        }.joinToString(separator = DETECTIONS_SEPARATOR) {
            "{${it.centerX()} ${it.centerY()}}"
        }
        deviceRepository.sendData(data)
    }

    companion object {
        private const val DETECTIONS_SEPARATOR = "|"
        private const val NO_DETECTIONS = "NoDetections"
    }
}