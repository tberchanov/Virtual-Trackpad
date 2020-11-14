package com.example.view

import com.example.parser.CommandParser
import com.example.processor.CommandProcessor
import data.DeviceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import kotlin.coroutines.CoroutineContext

class MainPresenterImpl(
    private val deviceRepository: DeviceRepository,
    private val commandParser: CommandParser,
    private val commandProcessor: CommandProcessor
) : MainPresenter, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private var view: MainView? = null

    override fun init(view: MainView) {
        this.view = view
    }

    override fun loadDetections() {
        launch {
            deviceRepository.waitForConnection()

            while (true) {
                val command = commandParser.parse(deviceRepository.readData())

                withContext(Dispatchers.JavaFx) {
                    if (!commandProcessor.processCommand(command)) {
                        println("Command was not processed: $command")
                    }
                }
            }
        }
    }

    override fun clear() {
        job.cancel()
        view = null
        deviceRepository.closeConnection()
    }
}