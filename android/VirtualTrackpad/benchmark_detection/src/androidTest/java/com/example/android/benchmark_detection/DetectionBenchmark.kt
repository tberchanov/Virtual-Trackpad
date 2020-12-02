package com.example.android.benchmark_detection

import android.content.Context
import android.graphics.BitmapFactory
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.virtualtrackpad.ObjectDetector
import com.example.android.virtualtrackpad.camera.util.ImageUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetectionBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val assetManager = context.assets

    private var inputArray = IntArray(MODEL_INPUT_SIZE * MODEL_INPUT_SIZE)

    @Before
    fun setUp() {
        val imageBitmap = assetManager.open("finger_resized.jpeg").use { stream ->
            BitmapFactory.decodeStream(stream)
        }
        ImageUtil.storePixels(imageBitmap, inputArray)
    }

    @Test
    fun detect_CPU_one_thread() {
        detectCpuWithThreads(1)
    }

    @Test
    fun detect_CPU_two_threads() {
        detectCpuWithThreads(2)
    }

    @Test
    fun detect_CPU_three_threads() {
        detectCpuWithThreads(3)
    }

    @Test
    fun detect_CPU_four_threads() {
        detectCpuWithThreads(4)
    }

    @Test
    fun detect_CPU_five_threads() {
        detectCpuWithThreads(5)
    }

    @Test
    fun detect_CPU_six_threads() {
        detectCpuWithThreads(6)
    }

    @Test
    fun detect_CPU_NNAPI_one_thread() {
        detectCpuWithThreads(1, nnapiEnabled = true)
    }

    @Test
    fun detect_CPU_NNAPI_two_threads() {
        detectCpuWithThreads(2, nnapiEnabled = true)
    }

    @Test
    fun detect_CPU_NNAPI_three_threads() {
        detectCpuWithThreads(3, nnapiEnabled = true)
    }

    @Test
    fun detect_CPU_NNAPI_four_threads() {
        detectCpuWithThreads(4, nnapiEnabled = true)
    }

    @Test
    fun detect_CPU_NNAPI_five_threads() {
        detectCpuWithThreads(5, nnapiEnabled = true)
    }

    @Test
    fun detect_CPU_NNAPI_six_threads() {
        detectCpuWithThreads(6, nnapiEnabled = true)
    }

    private fun detectCpuWithThreads(threadsCount: Int, nnapiEnabled: Boolean = false) {
        val detector = ObjectDetector(
            assetManager, MODEL_NAME,
            inputSize = MODEL_INPUT_SIZE, numThreads = threadsCount, useNnapi = nnapiEnabled
        )
        benchmarkRule.measureRepeated {
            detector.detect(inputArray)
        }
    }

    companion object {
        private const val MODEL_NAME = "model.tflite"
        private const val MODEL_INPUT_SIZE = 320
    }
}
/*
714 086 875 ns DetectionBenchmark.detect_CPU_one_thread
381 934 636 ns DetectionBenchmark.detect_CPU_two_threads
276 355 781 ns DetectionBenchmark.detect_CPU_three_threads
221 829 115 ns DetectionBenchmark.detect_CPU_four_threads
189 999 584 ns DetectionBenchmark.detect_CPU_five_threads
173 611 511 ns DetectionBenchmark.detect_CPU_six_threads

383 402 708 ns DetectionBenchmark.detect_CPU_NNAPI_two_threads
276 672 135 ns DetectionBenchmark.detect_CPU_NNAPI_three_threads
175 401 875 ns DetectionBenchmark.detect_CPU_NNAPI_six_threads
*/
