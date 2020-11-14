package com.example

import com.example.di.commandProcessorModule
import com.example.di.dataModule
import com.example.di.parserModule
import com.example.di.presenterModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import tornadofx.launch

fun main() {
    startKoin {
        printLogger()
        modules(
            dataModule,
            presenterModule,
            parserModule,
            commandProcessorModule
        )
    }
    launch<Application>()
}