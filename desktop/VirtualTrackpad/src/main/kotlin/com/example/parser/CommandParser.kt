package com.example.parser

import com.example.model.command.Command

interface CommandParser {

    fun setNext(commandParser: CommandParser)

    fun parse(text: String): Command
}