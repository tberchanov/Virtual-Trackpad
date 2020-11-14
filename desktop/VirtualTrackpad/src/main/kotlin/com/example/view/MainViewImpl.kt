package com.example.view

import com.example.Styles
import com.example.util.CursorUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import tornadofx.View
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label
import java.awt.Point

@Suppress("EXPERIMENTAL_API_USAGE")
class MainViewImpl : View("Hello TornadoFX"), MainView, KoinComponent {

    private val presenter: MainPresenter by (this as KoinComponent).inject()

    override val root = hbox {
        provideMainViewToDI()

        setupUI()

        presenter.init(this@MainViewImpl)
        presenter.loadDetections()
    }

    private fun provideMainViewToDI() {
        (this as KoinComponent).getKoin()
            .loadModules(
                listOf(module {
                    factory<MainView> { this@MainViewImpl }
                })
            )
    }

    private fun setupUI() {
        label(title) {
            addClass(Styles.heading)
        }
    }

    override fun onDelete() {
        presenter.clear()
        super.onDelete()
    }

    override fun moveCursor(x: Int, y: Int, movementTimeMillis: Long) {
        CursorUtils.moveCursorAnimated(x, y, movementTimeMillis)
    }

    override fun getCursorPosition(): Point {
        return CursorUtils.getCursorPosition()
    }
}
