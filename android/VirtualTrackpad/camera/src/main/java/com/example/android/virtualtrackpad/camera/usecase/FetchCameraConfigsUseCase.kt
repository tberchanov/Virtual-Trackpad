package com.example.android.virtualtrackpad.camera.usecase

import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import com.example.android.virtualtrackpad.camera.di.DetectionAnalyzerFactory
import com.example.android.virtualtrackpad.camera.model.CameraConfigs
import com.example.android.virtualtrackpad.core.DetectionConfigs
import com.example.android.virtualtrackpad.core.DetectionConfigsRepository
import java.util.concurrent.Executor
import javax.inject.Inject

class FetchCameraConfigsUseCase @Inject constructor(
    private val detectionConfigsRepository: DetectionConfigsRepository,
    private val detectionAnalyzerFactory: DetectionAnalyzerFactory
) {

    internal suspend fun execute(params: Params): CameraConfigs {
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

    internal data class Params(
        val executor: Executor,
        val onResultDetected: (ObjectDetectorAnalyzer.Result, drawDetections: Boolean) -> Unit
    )
}