package com.example.parser

import com.example.model.Detection
import com.example.model.command.Command
import com.example.model.command.MoveCursorCommand

class MoveCursorCommandParser : BaseCommandParser() {

    override fun parse(text: String): Command {
        return try {
            parseMoveCursorCommand(text)
        } catch (e: Exception) {
            nextParser!!.parse(text)
        }
    }

    private fun parseMoveCursorCommand(text: String): MoveCursorCommand {
        return text.split("|")
            .map { detectionString ->
                detectionString
                    .replace("{", "")
                    .replace("}", "")
                    .split(" ").let {
                    Detection(x = it[0].toFloat(), y = it[1].toFloat())
                }
            }
            .firstOrNull()
            ?.let {
                MoveCursorCommand(
                    moveToX = it.x,
                    moveToY = it.y
                )
            }!!
    }
}