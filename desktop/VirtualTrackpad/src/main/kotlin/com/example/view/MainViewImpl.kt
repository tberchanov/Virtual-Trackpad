package com.example.view

import com.example.util.CursorUtils
import javafx.beans.property.BooleanProperty
import javafx.beans.property.DoubleProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.StringProperty
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import tornadofx.*
import java.awt.Point

@Suppress("EXPERIMENTAL_API_USAGE")
class MainViewImpl : View(VIRTUAL_TRACKPAD_TITLE), MainView, KoinComponent {

    private val presenter: MainPresenter by (this as KoinComponent).inject()

    private var progressVisibility: BooleanProperty? = null
    private var progressWidth: DoubleProperty? = null
    private var progressHeight: DoubleProperty? = null

    private var labelText: StringProperty? = null
    private var labelVisibility: BooleanProperty? = null
    private var labelTextColor: ObjectProperty<Paint>? = null

    private var waitForConnectionButtonDisabled: BooleanProperty? = null
    private var waitForConnectionButtonVisibility: BooleanProperty? = null

    private var closeConnectionButtonVisibility: BooleanProperty? = null

    override val root = vbox {
        provideMainViewToDI()

        setupUI()

        presenter.init(this@MainViewImpl)
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        currentWindow?.setOnCloseRequest {
            presenter.clear()
        }
    }

    private fun provideMainViewToDI() {
        (this as KoinComponent).getKoin()
            .loadModules(
                listOf(module {
                    factory<MainView> { this@MainViewImpl }
                })
            )
    }

    private fun EventTarget.setupUI() =
        vbox(spacing = 30, alignment = Pos.TOP_CENTER) {
            prefWidth = 500.0
            prefHeight = 300.0
            paddingTop = 80

            stackpane {
                button(text = "Wait for device connection") {
                    waitForConnectionButtonVisibility = visibleProperty()
                    waitForConnectionButtonDisabled = disableProperty()
                    action {
                        presenter.onWaitForDeviceClicked()
                    }
                }
                button(text = "Close connection") {
                    isVisible = false
                    closeConnectionButtonVisibility = visibleProperty()
                    action {
                        presenter.onCloseConnectionClicked()
                    }
                }
            }
            progressindicator {
                progressWidth = prefWidthProperty()
                progressHeight = prefHeightProperty()
                progressVisibility = visibleProperty()
                setProgressVisibility(false)
            }
            label {
                font = Font.font(20.0)
                labelTextColor = textFillProperty()
                labelText = textProperty()
                labelVisibility = visibleProperty()
                textAlignment = TextAlignment.CENTER
                hideLabel()
            }
        }

    override fun showWaitForConnectionButton() {
        waitForConnectionButtonVisibility?.set(true)
        closeConnectionButtonVisibility?.set(false)
    }

    override fun showCloseConnectionButton() {
        closeConnectionButtonVisibility?.set(true)
        waitForConnectionButtonVisibility?.set(false)
    }

    override fun setProgressVisibility(visible: Boolean) {
        val progressSize = if (visible) 50.0 else 0.0
        progressWidth?.set(progressSize)
        progressHeight?.set(progressSize)
        progressVisibility?.set(visible)
    }

    override fun showError(text: String) {
        labelTextColor?.set(Color.RED)
        labelVisibility?.set(true)
        labelText?.set(text)
    }

    override fun showMessage(text: String) {
        labelTextColor?.set(Color.BLACK)
        labelVisibility?.set(true)
        labelText?.set(text)
    }

    override fun hideLabel() {
        labelVisibility?.set(false)
    }

    override fun disableWaitForConnectionButton(disabled: Boolean) {
        waitForConnectionButtonDisabled?.set(disabled)
    }

    override fun moveCursor(x: Int, y: Int, movementTimeMillis: Long) {
        CursorUtils.moveCursorAnimated(x, y, movementTimeMillis)
    }

    override fun getCursorPosition(): Point {
        return CursorUtils.getCursorPosition()
    }

    companion object {
        private const val VIRTUAL_TRACKPAD_TITLE = "Virtual Trackpad"
    }
}
