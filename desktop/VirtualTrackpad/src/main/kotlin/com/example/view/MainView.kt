package com.example.view

import com.example.Detection
import com.example.Styles
import com.example.data.BluetoothDeviceRepository
import data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.View
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label
import ui.util.getCursorPosition
import ui.util.moveCursorAnimated
import kotlin.math.abs

class MainView : View("Hello TornadoFX") {

    private val deviceRepository: DeviceRepository = BluetoothDeviceRepository()

    override val root = hbox {
        label(title) {
            addClass(Styles.heading)
        }

        loadDetections()
    }

    private fun loadDetections() {
        GlobalScope.launch(Dispatchers.IO) {
            deviceRepository.waitForConnection()

            while (true) {
                val detections = stringToDetections(deviceRepository.readData())

                detections.firstOrNull()?.let { detection ->
                    withContext(Dispatchers.JavaFx) {
                        processDetection(detection)
                    }
                }
            }
        }
    }

    private fun stringToDetections(string: String): List<Detection> {
        return try {
            string.split("|")
                .map { detectionString ->
                    detectionString.split(" ").let {
                        Detection(x = it[0].toFloat(), y = it[1].toFloat())
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private var previousDetection: Detection? = null

    private var previousDetectionTime = 0L

    private fun processDetection(detection: Detection) {
        val currentDetectionTime = System.currentTimeMillis()
        previousDetection?.let {
            val deltaX = convertToScreenPosition(it.x - detection.x)
            val deltaY = convertToScreenPosition(it.y - detection.y)

            if (abs(deltaX) > MOVE_THRESHOLD || abs(deltaY) > MOVE_THRESHOLD) {
                val currentCursorPosition = getCursorPosition()
                moveCursorAnimated(
                    currentCursorPosition.x + deltaX,
                    currentCursorPosition.y + deltaY,
                    currentDetectionTime - previousDetectionTime
                )
            }
        }
        previousDetection = detection
        previousDetectionTime = currentDetectionTime
    }

    private fun convertToScreenPosition(position: Float): Int {
        return (position * 10).toInt()
    }

    companion object {

        private const val MOVE_THRESHOLD = 40
    }
}
