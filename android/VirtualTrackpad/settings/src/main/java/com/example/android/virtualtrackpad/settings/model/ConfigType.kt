package com.example.android.virtualtrackpad.settings.model

internal enum class ConfigType {
    MINIMUM_CONFIDENCE,
    MULTIPLE_DETECTIONS_ENABLED,
    PREVIEW_ENABLED,
    DRAW_DETECTIONS_ENABLED,
    IS_QUANTIZED, // TODO need to create quantized module
    NNAPI_ENABLED,
    GPU_ENABLED,
    THREADS_QUANTITY
}