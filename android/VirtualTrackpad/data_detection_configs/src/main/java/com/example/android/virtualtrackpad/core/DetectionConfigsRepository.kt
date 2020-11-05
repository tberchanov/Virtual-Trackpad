package com.example.android.virtualtrackpad.core

interface DetectionConfigsRepository {

    suspend fun getConfigs(): DetectionConfigs

    suspend fun saveConfigs(configs: DetectionConfigs)
}