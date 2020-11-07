package com.example.android.virtualtrackpad.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraViewModel @ViewModelInject constructor(
    private val fetchCameraConfigsUseCase: FetchCameraConfigsUseCase,
    private val cameraPermissionsResolver: CameraPermissionsResolver
) : ViewModel() {

    val detectionResult = MutableLiveData<ObjectDetectorAnalyzer.Result>()

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun resolveCameraPermissions(
        onSuccess: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        cameraPermissionsResolver.checkAndRequestPermissionsIfNeeded(
            onSuccess, onFail
        )
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

    fun fetchCameraConfigs(): LiveData<CameraConfigs> {
        val cameraConfigs = MutableLiveData<CameraConfigs>()
        viewModelScope.launch(Dispatchers.IO) {
            fetchCameraConfigsUseCase.execute(
                FetchCameraConfigsUseCase.Params(executor, ::onResultDetected)
            ).let {
                cameraConfigs.postValue(it)
            }
        }
        return cameraConfigs
    }

    private fun onResultDetected(result: ObjectDetectorAnalyzer.Result, drawDetections: Boolean) {
        if (drawDetections) {
            detectionResult.value = result
        }
    }

    override fun onCleared() {
        executor.shutdown()
        super.onCleared()
    }
}