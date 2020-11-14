package com.example

import com.example.view.MainViewImpl
import org.koin.core.component.KoinApiExtension
import tornadofx.App

class Application : App(MainViewImpl::class, Styles::class)