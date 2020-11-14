package com.example.processor

import com.example.model.command.Command
import com.example.model.command.MoveCursorCommand
import com.example.model.command.NoDetectionsCommand
import com.example.view.MainView
import kotlin.math.abs

class MoveCursorCommandProcessor(
    private val mainView: MainView
) : CommandProcessor {

    private var previousMoveCursorCommand: MoveCursorCommand? = null

    private var previousMoveCursorTimeMillis: Long = UNDEFINED_MOVE_CURSOR_TIME

    override fun processCommand(
        command: Command
    ) = when (command) {
        is NoDetectionsCommand -> {
            dropPreviousMoveCursorCommand()
            true
        }
        is MoveCursorCommand -> {
            processMoveCursorCommand(command)
            true
        }
        else -> false
    }

    private fun dropPreviousMoveCursorCommand() {
        previousMoveCursorCommand = null
        previousMoveCursorTimeMillis = UNDEFINED_MOVE_CURSOR_TIME
    }

    private fun processMoveCursorCommand(command: MoveCursorCommand) {
        val currentMoveCursorTimeMillis = System.currentTimeMillis()
        previousMoveCursorCommand?.let {
            moveCursor(
                it,
                previousMoveCursorTimeMillis,
                command,
                currentMoveCursorTimeMillis
            )
        }
        previousMoveCursorCommand = command
        previousMoveCursorTimeMillis = currentMoveCursorTimeMillis
    }

    private fun moveCursor(
        previousMoveCursorCommand: MoveCursorCommand,
        previousMoveCursorTimeMillis: Long,
        currentMoveCursorCommand: MoveCursorCommand,
        currentMoveCursorTimeMillis: Long
    ) {
        val deltaX = convertToScreenPosition(
            previousMoveCursorCommand.moveToX - currentMoveCursorCommand.moveToX
        )
        val deltaY = convertToScreenPosition(
            previousMoveCursorCommand.moveToY - currentMoveCursorCommand.moveToY
        )

        if (abs(deltaX) > MOVE_THRESHOLD || abs(deltaY) > MOVE_THRESHOLD) {
            val currentCursorPosition = mainView.getCursorPosition()
            mainView.moveCursor(
                currentCursorPosition.x + deltaX,
                currentCursorPosition.y + deltaY,
                currentMoveCursorTimeMillis - previousMoveCursorTimeMillis
            )
        }
    }

    private fun convertToScreenPosition(position: Float): Int {
        return (position * 10).toInt()
    }

    companion object {
        private const val UNDEFINED_MOVE_CURSOR_TIME = -1L

        private const val MOVE_THRESHOLD = 40
    }
}