package com.example.android.virtualtrackpad

import android.content.res.AssetManager
import android.graphics.RectF
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import java.nio.ByteBuffer

class ObjectDetector constructor(
    assetManager: AssetManager,
    modelFilename: String,
    labelFilename: String? = null,
    private val useNnapi: Boolean = false,
    private val useGPU: Boolean = false,
    private val numThreads: Int = DetectorUtils.NUM_THREADS,
    private val minimumConfidence: Float = 0.0f,
    private val numDetections: Int = 10,
    private val inputSize: Int = 0,
    private val isModelQuantized: Boolean = false,
    private val multipleDetectionsEnabled: Boolean = false
) {

    private var labels: List<String> = emptyList()

    private val interpreter: Interpreter

    private val imgData: ByteBuffer

    init {
        labels = if (labelFilename.isNullOrEmpty()) {
            listOf("test")
        } else {
            DetectorUtils.loadLabelsFile(assetManager, labelFilename)
        }

        imgData = DetectorUtils.createImageBuffer(inputSize, isModelQuantized)

        interpreter = Interpreter(
            DetectorUtils.loadModelFile(assetManager, modelFilename),
            getInterpreterOptions()
        )
    }

    private fun getInterpreterOptions(): Interpreter.Options {
        val options = Interpreter.Options()
            .setNumThreads(numThreads)
            .setUseNNAPI(useNnapi)

        val compatList = CompatibilityList()
        if (compatList.isDelegateSupportedOnThisDevice && useGPU) {
            options.addDelegate(createGpuDelegate())
        } else if (useGPU) {
            Log.e("ObjectDetector", "This device is not supporting GPU.")
        }

        return options
    }

    private fun createGpuDelegate(): GpuDelegate {
        val compatList = CompatibilityList()
        val delegateOptions = compatList.bestOptionsForThisDevice
        return GpuDelegate(delegateOptions)
    }

    fun detect(pixels: IntArray): List<DetectionResult> {
        DetectorUtils.fillBuffer(
            imgData = imgData,
            pixels = pixels,
            inputSize = inputSize,
            isModelQuantized = isModelQuantized
        )

        val inputArray = arrayOf(imgData)
        val resultHolder = InterpreterResultHolder(numDetections)

        interpreter.runForMultipleInputsOutputs(inputArray, resultHolder.createOutputMap())

        return collectDetectionResult(resultHolder)
    }

    private fun collectDetectionResult(holder: InterpreterResultHolder): List<DetectionResult> {
        val result = mutableListOf<DetectionResult>()

        for (i in 0 until holder.detections) {
            val confidence = holder.outputScores[0][i]
            if (confidence < minimumConfidence) continue

            val title = labels[holder.outputClasses[0][i].toInt()]

            val location = RectF(
                holder.outputLocations[0][i][1] * inputSize,
                holder.outputLocations[0][i][0] * inputSize,
                holder.outputLocations[0][i][3] * inputSize,
                holder.outputLocations[0][i][2] * inputSize
            )

            result.add(
                DetectionResult(
                    id = i,
                    title = title,
                    confidence = confidence,
                    location = location
                )
            )
        }

        return if (result.size > 1 && !multipleDetectionsEnabled) {
            listOf(result.maxByOrNull { it.confidence }!!)
        } else {
            result
        }
    }
}