package com.example.parser

import com.example.model.command.Command
import com.example.model.command.UndefinedCommand

class UndefinedCommandParser : BaseCommandParser() {

    override fun parse(text: String): Command = UndefinedCommand()
}