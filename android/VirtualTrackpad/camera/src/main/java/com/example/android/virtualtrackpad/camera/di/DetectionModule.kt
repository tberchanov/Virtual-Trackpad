package com.example.android.virtualtrackpad.camera.di

import android.content.Context
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