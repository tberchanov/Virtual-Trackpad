package com.example.model.command

data class MoveCursorCommand(
    val moveToX: Float,
    val moveToY: Float,
) : Command