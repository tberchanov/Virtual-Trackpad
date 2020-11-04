package com.example.android.virtualtrackpad.camera

import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraViewModel @ViewModelInject constructor(
    private val analyzer: ObjectDetectorAnalyzer
) : ViewModel() {

    val detectionResult = MutableLiveData<ObjectDetectorAnalyzer.Result>()

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun getPreview(): Preview? {
        // TODO can be null in case when preview is disabled from settings
        return Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()
    }

    fun getImageAnalysis() = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().apply {
            setAnalyzer(
                executor,
                analyzer.also {
                    it.onDetectionResult = ::onResultDetected
                }
            )
        }

    private fun onResultDetected(result: ObjectDetectorAnalyzer.Result) {
        detectionResult.value = result
    }

    fun getProcessCameraProvider(
        context: Context,
        onDone: (ProcessCameraProvider) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            { onDone.invoke(cameraProviderFuture.get()) },
            ContextCompat.getMainExecutor(context)
        )
    }

    override fun onCleared() {
        executor.shutdown()
        super.onCleared()
    }
}