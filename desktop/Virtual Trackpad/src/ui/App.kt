package ui

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.awt.AWTException
import java.awt.Robot

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}

class App : Application() {

    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("sample.fxml"))
        primaryStage.title = "Hello World"
        primaryStage.scene = Scene(root, 300.0, 275.0)
        primaryStage.show()

        moveCursor(0, 0)
    }

    companion object {

        /**
         * Move the mouse to the specific screen position
         *
         * @param screenX
         * @param screenY
         */
        private fun moveCursor(screenX: Int, screenY: Int) {
            Platform.runLater {
                try {
                    val robot = Robot()
                    robot.mouseMove(screenX, screenY)
                } catch (e: AWTException) {
                    e.printStackTrace()
                }
            }
        }
    }
}