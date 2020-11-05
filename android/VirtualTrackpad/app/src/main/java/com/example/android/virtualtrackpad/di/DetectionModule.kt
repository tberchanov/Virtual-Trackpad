package com.example.android.virtualtrackpad.di

import android.content.Context
import com.example.android.virtualtrackpad.detection.DetectionAnalyzerFactory
import com.example.android.virtualtrackpad.detection.DetectionAnalyzerFactoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
class DetectionModule {

    @Provides
    fun provideDetectionAnalyzerFactory(
        @ApplicationContext context: Context
    ): DetectionAnalyzerFactory = DetectionAnalyzerFactoryImpl(context)
}