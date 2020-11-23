package com.example.view

import java.awt.Point

interface MainView {

    fun moveCursor(x: Int, y: Int, movementTimeMillis: Long)

    fun getCursorPosition(): Point

    fun setProgressVisibility(visible: Boolean)

    fun showError(text: String)

    fun showMessage(text: String)

    fun hideLabel()

    fun disableWaitForConnectionButton(disabled: Boolean)

    fun showWaitForConnectionButton()

    fun showCloseConnectionButton()

}