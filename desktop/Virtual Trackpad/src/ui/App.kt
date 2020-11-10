package ui

import data.BluetoothDeviceRepository
import data.DeviceRepository
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.util.getCursorPosition
import ui.util.moveCursor
import kotlin.math.abs

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}

class App : Application() {

    private val deviceRepository: DeviceRepository = BluetoothDeviceRepository()

    override fun start(primaryStage: Stage) {
        setupUI(primaryStage)
        loadDetections()
    }

    private fun setupUI(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("sample.fxml"))
        primaryStage.title = "Hello World"
        primaryStage.scene = Scene(root, 300.0, 275.0)
        primaryStage.show()
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

    private fun processDetection(detection: Detection) {
        previousDetection?.let {
            val deltaX = convertToScreenPosition(it.x - detection.x)
            val deltaY = convertToScreenPosition(it.y - detection.y)

            if (abs(deltaX) > MOVE_THRESHOLD || abs(deltaY) > MOVE_THRESHOLD) {
                val currentCursorPosition = getCursorPosition()
                moveCursor(
                    currentCursorPosition.x + deltaX,
                    currentCursorPosition.y + deltaY
                )
            }
        }
        previousDetection = detection
    }

    private fun convertToScreenPosition(position: Float): Int {
        return (position * 10).toInt()
    }

    companion object {

        private const val MOVE_THRESHOLD = 40
    }
}