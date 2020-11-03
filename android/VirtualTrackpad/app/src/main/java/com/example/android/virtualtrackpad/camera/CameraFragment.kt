package com.example.android.virtualtrackpad.camera

import android.os.Bundle
import android.view.View
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.android.virtualtrackpad.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_camera.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var executor: ExecutorService

    private val cameraPermissionsResolver by lazy { CameraPermissionsResolver(requireActivity()) }

    private val objectDetectorConfig = ObjectDetectorAnalyzer.Config(
        minimumConfidence = 0.8f,
        numDetection = 10,
        inputSize = 320,
        isQuantized = false,
        modelFile = "model.tflite",
        labelsFile = "labelmap.txt",
        multipleDetectionsEnabled = false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executor = Executors.newSingleThreadExecutor()

        cameraPermissionsResolver.checkAndRequestPermissionsIfNeeded(
            onSuccess = {
                getProcessCameraProvider(::bindCamera)
            },
            onFail = ::showSnackbar
        )
    }

    override fun onDestroyView() {
        executor.shutdown()
        super.onDestroyView()
    }

    private fun bindCamera(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            executor,
            ObjectDetectorAnalyzer(requireContext(), objectDetectorConfig, ::onDetectionResult)
        )

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            imageAnalysis,
            preview
        )

        preview.setSurfaceProvider(preview_view.surfaceProvider)
    }

    private fun getProcessCameraProvider(onDone: (ProcessCameraProvider) -> Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            { onDone.invoke(cameraProviderFuture.get()) },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun onDetectionResult(result: ObjectDetectorAnalyzer.Result) {
        result_overlay.updateResults(result)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(root_container, message, Snackbar.LENGTH_LONG).show()
    }
}