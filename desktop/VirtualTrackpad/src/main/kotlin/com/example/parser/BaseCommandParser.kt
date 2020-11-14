package com.example.parser

abstract class BaseCommandParser : CommandParser {

    protected var nextParser: CommandParser? = null

    override fun setNext(commandParser: CommandParser) {
        nextParser = commandParser
    }
}