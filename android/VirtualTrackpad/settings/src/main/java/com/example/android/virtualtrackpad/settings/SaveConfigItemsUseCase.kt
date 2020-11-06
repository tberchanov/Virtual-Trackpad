package com.example.android.virtualtrackpad.settings

import com.example.android.virtualtrackpad.core.DetectionConfigs
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_DRAW_DETECTIONS_ENABLED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_GPU_ENABLED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_INPUT_SIZE
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_IS_QUANTIZED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_LABELS_FILE
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_MINIMUM_CONFIDENCE
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_MODEL_FILE
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_MULTIPLE_DETECTIONS_ENABLED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_NNAPI_ENABLED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_NUM_DETECTIONS
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_PREVIEW_ENABLED
import com.example.android.virtualtrackpad.core.DetectionConfigs.Companion.DEFAULT_THREADS_QUANTITY
import com.example.android.virtualtrackpad.core.DetectionConfigsRepository
import com.example.android.virtualtrackpad.settings.model.ConfigItem
import com.example.android.virtualtrackpad.settings.model.ConfigType
import com.example.android.virtualtrackpad.settings.model.ConfigType.*
import javax.inject.Inject

class SaveConfigItemsUseCase @Inject constructor(
    private val configsRepository: DetectionConfigsRepository
) {

    internal suspend fun execute(configItems: List<ConfigItem>) {
        DetectionConfigs(
            numDetection = DEFAULT_NUM_DETECTIONS,
            inputSize = DEFAULT_INPUT_SIZE,
            modelFile = DEFAULT_MODEL_FILE,
            labelsFile = DEFAULT_LABELS_FILE,
            minimumConfidence = findFloatConfig(
                configItems,
                MINIMUM_CONFIDENCE,
                DEFAULT_MINIMUM_CONFIDENCE
            ),
            isQuantized = findBooleanConfig(
                configItems,
                IS_QUANTIZED,
                DEFAULT_IS_QUANTIZED
            ),
            multipleDetectionsEnabled = findBooleanConfig(
                configItems,
                MULTIPLE_DETECTIONS_ENABLED,
                DEFAULT_MULTIPLE_DETECTIONS_ENABLED
            ),
            previewEnabled = findBooleanConfig(
                configItems,
                PREVIEW_ENABLED,
                DEFAULT_PREVIEW_ENABLED
            ),
            drawDetectionsEnabled = findBooleanConfig(
                configItems,
                DRAW_DETECTIONS_ENABLED,
                DEFAULT_DRAW_DETECTIONS_ENABLED
            ),
            nnapiEnabled = findBooleanConfig(
                configItems,
                NNAPI_ENABLED,
                DEFAULT_NNAPI_ENABLED
            ),
            gpuEnabled = findBooleanConfig(
                configItems,
                GPU_ENABLED,
                DEFAULT_GPU_ENABLED
            ),
            threadsQuantity = findIntConfig(
                configItems,
                THREADS_QUANTITY,
                DEFAULT_THREADS_QUANTITY
            )
        ).let {
            configsRepository.saveConfigs(it)
        }
    }

    private fun findFloatConfig(
        configItems: List<ConfigItem>,
        configType: ConfigType,
        defaultValue: Float
    ): Float {
        return configItems.find {
            it.type == configType
        }?.textValue?.toFloat() ?: defaultValue
    }

    private fun findBooleanConfig(
        configItems: List<ConfigItem>,
        configType: ConfigType,
        defaultValue: Boolean
    ): Boolean {
        return configItems.find {
            it.type == configType
        }?.boolValue ?: defaultValue
    }

    private fun findIntConfig(
        configItems: List<ConfigItem>,
        configType: ConfigType,
        defaultValue: Int
    ): Int {
        return configItems.find {
            it.type == configType
        }?.textValue?.toInt() ?: defaultValue
    }
}