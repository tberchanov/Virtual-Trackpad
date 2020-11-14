package com.example.view

interface MainPresenter {

    fun init(view: MainView)

    fun clear()

    fun loadDetections()
}