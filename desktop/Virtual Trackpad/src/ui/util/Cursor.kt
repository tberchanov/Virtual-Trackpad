package ui.util

import com.sun.glass.ui.Application
import javafx.application.Platform
import java.awt.AWTException
import java.awt.Point
import java.awt.Robot

/**
 * Move the mouse to the specific screen position
 *
 * @param screenX
 * @param screenY
 */
fun moveCursor(screenX: Int, screenY: Int) {
    Platform.runLater {
        try {
            val robot = Robot()
            robot.mouseMove(screenX, screenY)
        } catch (e: AWTException) {
            e.printStackTrace()
        }
    }
}

fun getCursorPosition(): Point {
    val robot = Application.GetApplication().createRobot()
    return Point(robot.mouseX, robot.mouseY)
}