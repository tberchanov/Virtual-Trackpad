package ui.util

import com.sun.glass.ui.Application
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.beans.value.WritableValue
import javafx.util.Duration
import java.awt.AWTException
import java.awt.Point

private val robot = Application.GetApplication().createRobot()

/**
 * Move the mouse to the specific screen position
 *
 * @param screenX
 * @param screenY
 */
fun moveCursor(screenX: Int, screenY: Int) {
    Platform.runLater {
        try {
            robot.mouseMove(screenX, screenY)
        } catch (e: AWTException) {
            e.printStackTrace()
        }
    }
}

fun getCursorPosition(): Point {
    return Point(robot.mouseX, robot.mouseY)
}

private fun getCursorPropertyX(): WritableValue<Int> {
    return object : WritableValue<Int> {

        override fun getValue(): Int {
            return robot.mouseX
        }

        override fun setValue(value: Int) {
            robot.mouseMove(value, robot.mouseY)
        }
    }
}

private fun getCursorPropertyY(): WritableValue<Int> {
    return object : WritableValue<Int> {

        override fun getValue(): Int {
            return robot.mouseY
        }

        override fun setValue(value: Int) {
            robot.mouseMove(robot.mouseX, value)
        }
    }
}

/**
 * Move the mouse to the specific screen position
 *
 * @param screenX
 * @param screenY
 * @param durationMillis - duration of animation
 */
fun moveCursorAnimated(screenX: Int, screenY: Int, durationMillis: Long) {
    Platform.runLater {
        val timeline = Timeline(
            KeyFrame(
                Duration.millis(durationMillis.toDouble()),
                KeyValue(getCursorPropertyX(), screenX),
                KeyValue(getCursorPropertyY(), screenY)
            )
        )
        timeline.cycleCount = 1
        timeline.play()
    }
}