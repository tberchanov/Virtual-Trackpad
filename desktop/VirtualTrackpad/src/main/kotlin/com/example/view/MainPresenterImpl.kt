package com.example.view

import com.example.exceptions.ConnectionCorruptedException
import com.example.parser.CommandParser
import com.example.processor.CommandProcessor
import com.example.data.DeviceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import javax.bluetooth.BluetoothStateException
import kotlin.coroutines.CoroutineContext

class MainPresenterImpl(
    private val deviceRepository: DeviceRepository,
    private val commandParser: CommandParser,
    private val commandProcessor: CommandProcessor
) : MainPresenter, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private var view: MainView? = null

    private var stopLoadDetections = true

    override fun init(view: MainView) {
        this.view = view
    }

    override fun onWaitForDeviceClicked() {
        waitForConnection()
    }

    override fun onCloseConnectionClicked() {
        stopLoadDetections = true
        deviceRepository.closeConnection()
        view?.showWaitForConnectionButton()
        view?.hideLabel()
    }

    private fun waitForConnection() {
        view?.hideLabel()
        view?.setProgressVisibility(true)
        view?.disableWaitForConnectionButton(true)
        launch(Dispatchers.JavaFx) {
            try {
                withContext(Dispatchers.IO) {
                    deviceRepository.waitForConnection()
                }
            } catch (e: BluetoothStateException) {
                e.printStackTrace()
                showBluetoothDisabledError()
                return@launch
            }

            view?.showMessage("Device successfully connected!")
            view?.setProgressVisibility(false)
            view?.disableWaitForConnectionButton(false)
            view?.showCloseConnectionButton()

            stopLoadDetections = false
            loadDetections()
        }
    }

    private fun showBluetoothDisabledError() {
        showError("Please turn on Bluetooth")
    }

    private fun showConnectionCorruptedError() {
        showError("Your connection is corrupted!\nPlease, try to reconnect.")
    }

    private fun showError(errorText: String) {
        view?.setProgressVisibility(false)
        view?.disableWaitForConnectionButton(false)
        view?.showError(errorText)
        view?.showWaitForConnectionButton()
    }

    private suspend fun loadDetections() {
        while (!stopLoadDetections) {
            val command = try {
                withContext(Dispatchers.IO) {
                    commandParser.parse(deviceRepository.readData())
                }
            } catch (e: ConnectionCorruptedException) {
                if (!stopLoadDetections) {
                    showConnectionCorruptedError()
                }
                return
            }

            if (!commandProcessor.processCommand(command)) {
                println("Command was not processed: $command")
            }
        }
    }

    override fun clear() {
        job.cancel()
        view = null
        deviceRepository.closeConnection()
    }
}