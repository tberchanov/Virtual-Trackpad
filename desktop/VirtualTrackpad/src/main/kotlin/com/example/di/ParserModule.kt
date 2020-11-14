package com.example.di

import com.example.parser.CommandParser
import com.example.parser.MoveCursorCommandParser
import com.example.parser.NoDetectionsCommandParser
import com.example.parser.UndefinedCommandParser
import org.koin.dsl.module

val parserModule = module {
    factory<CommandParser> {
        val moveCursorCommandParser = MoveCursorCommandParser()
        val noDetectionsCommandParser = NoDetectionsCommandParser()
        val undefinedCommandParser = UndefinedCommandParser()

        moveCursorCommandParser.setNext(noDetectionsCommandParser)
        noDetectionsCommandParser.setNext(undefinedCommandParser)

        return@factory moveCursorCommandParser
    }
}