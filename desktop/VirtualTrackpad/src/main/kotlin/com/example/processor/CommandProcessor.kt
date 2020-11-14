package com.example.processor

import com.example.model.command.Command

interface CommandProcessor {

    fun processCommand(command: Command): Boolean
}