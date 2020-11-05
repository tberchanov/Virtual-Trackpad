package com.example.android.virtualtrackpad.core

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class DetectionConfigsRepositoryImpl(
    context: Context
) : DetectionConfigsRepository {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = DETECTION_CONFIGS_STORE_NAME
    )

    private val minimumConfidenceKey = preferencesKey<Float>(MINIMUM_CONFIDENCE_KEY)
    private val numDetectionKey = preferencesKey<Int>(NUM_DETECTIONS_KEY)
    private val inputSizeKey = preferencesKey<Int>(INPUT_SIZE_KEY)
    private val isQuantizedKey = preferencesKey<Boolean>(IS_QUANTIZED_KEY)
    private val modelFileKey = preferencesKey<String>(MODEL_FILE_KEY)
    private val labelsFileKey = preferencesKey<String>(LABELS_FILE_KEY)
    private val previewEnabledKey = preferencesKey<Boolean>(PREVIEW_ENABLED_KEY)
    private val multipleDetectionsEnabledKey =
        preferencesKey<Boolean>(MULTIPLE_DETECTIONS_ENABLED_KEY)

    override suspend fun getConfigs(): DetectionConfigs {
        return dataStore.data
            .map {
                DetectionConfigs(
                    minimumConfidence = it[minimumConfidenceKey] ?: DEFAULT_MINIMUM_CONFIDENCE,
                    numDetection = it[numDetectionKey] ?: DEFAULT_NUM_DETECTIONS,
                    inputSize = it[inputSizeKey] ?: DEFAULT_INPUT_SIZE,
                    isQuantized = it[isQuantizedKey] ?: DEFAULT_IS_QUANTIZED,
                    modelFile = it[modelFileKey] ?: DEFAULT_MODEL_FILE,
                    labelsFile = it[labelsFileKey] ?: DEFAULT_LABELS_FILE,
                    multipleDetectionsEnabled = it[multipleDetectionsEnabledKey]
                        ?: DEFAULT_MULTIPLE_DETECTIONS_ENABLED,
                    previewEnabled = it[previewEnabledKey] ?: DEFAULT_PREVIEW_ENABLED
                )
            }.first()
    }

    override suspend fun saveConfigs(configs: DetectionConfigs) {
        dataStore.edit {
            it.putAll(
                minimumConfidenceKey to configs.minimumConfidence,
                numDetectionKey to configs.numDetection,
                inputSizeKey to configs.inputSize,
                isQuantizedKey to configs.isQuantized,
                modelFileKey to configs.modelFile,
                labelsFileKey to configs.labelsFile,
                multipleDetectionsEnabledKey to configs.multipleDetectionsEnabled,
            )
        }
    }

    companion object {
        private const val DETECTION_CONFIGS_STORE_NAME = "detection_configs"
        private const val MINIMUM_CONFIDENCE_KEY = "MINIMUM_CONFIDENCE_KEY"
        private const val NUM_DETECTIONS_KEY = "NUM_DETECTIONS_KEY"
        private const val INPUT_SIZE_KEY = "INPUT_SIZE_KEY"
        private const val IS_QUANTIZED_KEY = "IS_QUANTIZED_KEY"
        private const val MODEL_FILE_KEY = "MODEL_FILE_KEY"
        private const val LABELS_FILE_KEY = "LABELS_FILE_KEY"
        private const val MULTIPLE_DETECTIONS_ENABLED_KEY = "MULTIPLE_DETECTIONS_ENABLED_KEY"
        private const val PREVIEW_ENABLED_KEY = "PREVIEW_ENABLED_KEY"

        private const val DEFAULT_MINIMUM_CONFIDENCE = 0.5f
        private const val DEFAULT_NUM_DETECTIONS = 10
        private const val DEFAULT_INPUT_SIZE = 320
        private const val DEFAULT_IS_QUANTIZED = false
        private const val DEFAULT_MODEL_FILE = "model.tflite"
        private const val DEFAULT_LABELS_FILE = ""
        private const val DEFAULT_MULTIPLE_DETECTIONS_ENABLED = false
        private const val DEFAULT_PREVIEW_ENABLED = true
    }
}