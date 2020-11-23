package com.example.android.virtualtrackpad.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.virtualtrackpad.camera.exception.SendDetectionsException
import com.example.android.virtualtrackpad.camera.model.CameraConfigs
import com.example.android.virtualtrackpad.camera.usecase.CloseConnectionUseCase
import com.example.android.virtualtrackpad.camera.usecase.FetchCameraConfigsUseCase
import com.example.android.virtualtrackpad.camera.usecase.ObjectDetectorAnalyzer
import com.example.android.virtualtrackpad.camera.usecase.SendDetectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class CameraViewModel @ViewModelInject constructor(
    private val fetchCameraConfigsUseCase: FetchCameraConfigsUseCase,
    private val sendDetectionsUseCase: SendDetectionsUseCase,
    private val closeConnectionUseCase: CloseConnectionUseCase,
) : ViewModel() {

    val detectionResult = MutableLiveData<ObjectDetectorAnalyzer.Result>()

    val sendDetectionException = MutableLiveData<SendDetectionsException>()

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    private val detectionResultsChannel = Channel<ObjectDetectorAnalyzer.Result>(Channel.UNLIMITED)

    init {
        processDetections()
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
        detectionResultsChannel.sendBlocking(result)
    }

    private fun processDetections() {
        viewModelScope.launch(Dispatchers.IO) {
            for (detection in detectionResultsChannel) {
                try {
                    sendDetectionsUseCase.execute(detection)
                } catch (e: IOException) {
                    Log.e("CameraViewModel", "Send data failure", e)
                    sendDetectionException.postValue(SendDetectionsException())
                }
            }
        }
    }

    override fun onCleared() {
        closeConnectionUseCase.execute()
        executor.shutdown()
        super.onCleared()
    }
}