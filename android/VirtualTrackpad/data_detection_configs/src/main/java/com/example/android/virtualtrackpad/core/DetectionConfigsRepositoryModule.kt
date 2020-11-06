package com.example.android.virtualtrackpad.core

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DetectionConfigsRepositoryModule {

    @Singleton
    @Provides
    fun provideDetectionConfigsRepository(
        @ApplicationContext context: Context
    ): DetectionConfigsRepository = DetectionConfigsRepositoryImpl(context)
}