package com.example.android.virtualtrackpad.di

import android.content.Context
import com.example.android.virtualtrackpad.camera.ObjectDetectorAnalyzer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
class DetectionModule {

    @Provides
    fun provideObjectDetectorAnalyzerConfig() = ObjectDetectorAnalyzer.Config(
        minimumConfidence = 0.8f,
        numDetection = 10,
        inputSize = 320,
        isQuantized = false,
        modelFile = "model.tflite",
        multipleDetectionsEnabled = false
    )

    @Provides
    fun provideObjectDetectorAnalyzer(
        @ApplicationContext context: Context,
        config: ObjectDetectorAnalyzer.Config
    ) = ObjectDetectorAnalyzer(
        context,
        config
    )
}