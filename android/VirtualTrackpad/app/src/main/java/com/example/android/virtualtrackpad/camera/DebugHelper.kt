package com.example.android.virtualtrackpad.camera

import android.content.Context
import android.graphics.*
import com.example.android.virtualtrackpad.detection.DetectionResult
import com.example.android.virtualtrackpad.util.ImageUtil

class DebugHelper(
    private val saveResult: Boolean,
    resultHeight: Int,
    resultWidth: Int,
    private val context: Context
) {

    private val resultBitmap: Bitmap by lazy {
        Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888)
    }
    private val matrix = Matrix()

    private val boxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 2.0f
    }

    fun saveResult(iteration: Int, resizedBitmap: Bitmap, result: List<DetectionResult>) {
        if (!saveResult) return

        Canvas(resultBitmap).let { canvas ->
            canvas.drawBitmap(resizedBitmap, matrix, null)
            result.forEach { canvas.drawRect(it.location, boxPaint) }
        }
        ImageUtil.saveBitmap(context, resultBitmap, "input_$iteration")
    }


}