package com.example.android.virtualtrackpad.camera

import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import com.example.android.virtualtrackpad.core.DetectionConfigs
import com.example.android.virtualtrackpad.core.DetectionConfigsRepository
import com.example.android.virtualtrackpad.detection.DetectionAnalyzerFactory
import java.util.concurrent.Executor
import javax.inject.Inject

class FetchCameraConfigsUseCase @Inject constructor(
    private val detectionConfigsRepository: DetectionConfigsRepository,
    private val detectionAnalyzerFactory: DetectionAnalyzerFactory
) {

    suspend fun execute(params: Params): CameraConfigs {
        val detectionConfigs = detectionConfigsRepository.getConfigs()

        return CameraConfigs(
            imageAnalysis = createImageAnalysis(params, detectionConfigs),
            preview = createPreview(detectionConfigs)
        )
    }

    private fun createImageAnalysis(
        params: Params,
        configs: DetectionConfigs
    ): ImageAnalysis {
        val analyzer = detectionAnalyzerFactory.createDetectionAnalyzer(
            configs
        ).apply { onDetectionResult = params.onResultDetected }

        return ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build().apply {
                setAnalyzer(
                    params.executor,
                    analyzer
                )
            }
    }

    private fun createPreview(configs: DetectionConfigs): Preview? {
        return if (configs.previewEnabled) {
            Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
        } else {
            null
        }
    }

    data class Params(
        val executor: Executor,
        val onResultDetected: (ObjectDetectorAnalyzer.Result) -> Unit
    )
}