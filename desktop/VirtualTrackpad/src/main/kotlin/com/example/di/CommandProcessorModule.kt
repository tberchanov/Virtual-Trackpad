package com.example.di

import com.example.processor.CommandProcessingContext
import com.example.processor.CommandProcessor
import com.example.processor.MoveCursorCommandProcessor
import com.example.view.MainView
import org.koin.dsl.module

val commandProcessorModule = module {
    factory<CommandProcessor> { CommandProcessingContext(getCommandProcessors(get())) }
}

private fun getCommandProcessors(mainView: MainView): List<CommandProcessor> = listOf(
    MoveCursorCommandProcessor(mainView)
)