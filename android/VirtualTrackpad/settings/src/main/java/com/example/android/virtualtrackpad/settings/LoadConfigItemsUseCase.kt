package com.example.android.virtualtrackpad.settings

import com.example.android.virtualtrackpad.core.DetectionConfigsRepository
import com.example.android.virtualtrackpad.settings.model.ConfigItem
import com.example.android.virtualtrackpad.settings.model.ConfigType.*
import javax.inject.Inject

class LoadConfigItemsUseCase @Inject constructor(
    private val configsRepository: DetectionConfigsRepository
) {

    internal suspend fun execute(): List<ConfigItem> {
        return configsRepository.getConfigs().let { configs ->
            listOf(
                ConfigItem(
                    type = MINIMUM_CONFIDENCE,
                    title = R.string.minimum_confidence,
                    textValue = configs.minimumConfidence.toString()
                ),
                ConfigItem(
                    type = MULTIPLE_DETECTIONS_ENABLED,
                    title = R.string.multiple_detections_enabled,
                    boolValue = configs.multipleDetectionsEnabled
                ),
                ConfigItem(
                    type = PREVIEW_ENABLED,
                    title = R.string.preview_enabled,
                    boolValue = configs.previewEnabled
                ),
                ConfigItem(
                    type = DRAW_DETECTIONS_ENABLED,
                    title = R.string.draw_detections_enabled,
                    boolValue = configs.drawDetectionsEnabled
                ),
                ConfigItem(
                    type = IS_QUANTIZED,
                    title = R.string.is_quantized,
                    boolValue = configs.isQuantized
                ),
                ConfigItem(
                    type = NNAPI_ENABLED,
                    title = R.string.nnapi_enabled,
                    boolValue = configs.nnapiEnabled
                ),
                ConfigItem(
                    type = GPU_ENABLED,
                    title = R.string.gpu_enabled,
                    boolValue = configs.gpuEnabled
                ),
                ConfigItem(
                    type = THREADS_QUANTITY,
                    title = R.string.threads_quantity,
                    textValue = configs.threadsQuantity.toString()
                ),
            )
        }
    }
}