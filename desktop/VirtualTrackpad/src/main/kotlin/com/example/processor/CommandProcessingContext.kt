package com.example.processor

import com.example.model.command.Command

class CommandProcessingContext(
    private val commandProcessors: List<CommandProcessor>
) : CommandProcessor {

    override fun processCommand(command: Command): Boolean {
        println("processCommand: $command")
        commandProcessors.forEach { processor ->
            if (processor.processCommand(command)) {
                return true
            }
        }
        return false
    }
}