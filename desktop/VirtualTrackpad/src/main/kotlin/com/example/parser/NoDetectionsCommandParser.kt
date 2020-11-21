package com.example.parser

import com.example.model.command.Command
import com.example.model.command.NoDetectionsCommand

class NoDetectionsCommandParser : BaseCommandParser() {

    override fun parse(text: String): Command {
        return if (text == NO_DETECTIONS) {
            NoDetectionsCommand()
        } else {
            nextParser!!.parse(text)
        }
    }

    companion object {
        private const val NO_DETECTIONS = "NoDetections"
    }
}