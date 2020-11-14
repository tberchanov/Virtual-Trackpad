package com.example.parser

import com.example.model.Detection
import com.example.model.command.Command
import com.example.model.command.MoveCursorCommand
import java.lang.Exception

class MoveCursorCommandParser : BaseCommandParser() {

    override fun parse(text: String): Command {
        // TODO need to refactor, use new Commands protocol
        return try {
            text.split("|")
                .map { detectionString ->
                    detectionString.split(" ").let {
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
        } catch (e: Exception) {
            e.printStackTrace()
            nextParser!!.parse(text)
        }
    }
}