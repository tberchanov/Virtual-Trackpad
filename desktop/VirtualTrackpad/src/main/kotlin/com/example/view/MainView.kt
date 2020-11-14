package com.example.view

import java.awt.Point

interface MainView {

    fun moveCursor(x: Int, y: Int, movementTimeMillis: Long)

    fun getCursorPosition(): Point

}