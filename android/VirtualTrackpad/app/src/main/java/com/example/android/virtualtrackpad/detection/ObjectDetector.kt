package com.example.android.virtualtrackpad.detection

import android.content.res.AssetManager
import android.graphics.RectF
import com.example.android.virtualtrackpad.util.DetectorUtils
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer

class ObjectDetector constructor(
    assetManager: AssetManager,
    modelFilename: String,
    labelFilename: String? = null,
    useNnapi: Boolean = false,
    numThreads: Int = DetectorUtils.NUM_THREADS,
    private val minimumConfidence: Float,
    private val numDetections: Int,
    private val inputSize: Int = 0,
    private val isModelQuantized: Boolean = false,
    private val multipleDetectionsEnabled: Boolean
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
            Interpreter.Options()
                .setNumThreads(numThreads)
                .setUseNNAPI(useNnapi)
        )
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