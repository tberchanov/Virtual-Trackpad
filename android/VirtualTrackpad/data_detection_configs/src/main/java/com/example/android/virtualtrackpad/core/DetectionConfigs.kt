package com.example.android.virtualtrackpad.core

data class DetectionConfigs(
    val minimumConfidence: Float,
    val numDetection: Int,
    val inputSize: Int,
    val isQuantized: Boolean,
    val modelFile: String,
    val labelsFile: String = "",
    val multipleDetectionsEnabled: Boolean,
    val previewEnabled: Boolean,
    val drawDetectionsEnabled: Boolean,
    val nnapiEnabled: Boolean,
    val gpuEnabled: Boolean,
    val threadsQuantity: Int
) {

    companion object {
        const val DEFAULT_MINIMUM_CONFIDENCE = 0.5f
        const val DEFAULT_NUM_DETECTIONS = 10
        const val DEFAULT_INPUT_SIZE = 320
        const val DEFAULT_IS_QUANTIZED = false
        const val DEFAULT_MODEL_FILE = "model.tflite"
        const val DEFAULT_LABELS_FILE = ""
        const val DEFAULT_MULTIPLE_DETECTIONS_ENABLED = false
        const val DEFAULT_PREVIEW_ENABLED = true
        const val DEFAULT_DRAW_DETECTIONS_ENABLED = true
        const val DEFAULT_NNAPI_ENABLED = false
        const val DEFAULT_GPU_ENABLED = false
        const val DEFAULT_THREADS_QUANTITY = 4
    }
}