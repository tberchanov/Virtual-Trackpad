package com.example.parser

import com.example.model.command.Command
import com.example.model.command.NoDetectionsCommand

class NoDetectionsCommandParser : BaseCommandParser() {

    override fun parse(text: String): Command {
        // TODO implement logic of parsing NoDetectionsCommand
        val isItNoDetectionsCommand = false
        return if (isItNoDetectionsCommand) {
            NoDetectionsCommand()
        } else {
            nextParser!!.parse(text)
        }
    }
}