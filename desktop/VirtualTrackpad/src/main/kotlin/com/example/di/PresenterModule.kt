package com.example.di

import com.example.view.MainPresenter
import com.example.view.MainPresenterImpl
import org.koin.dsl.module

val presenterModule = module {
    factory<MainPresenter> { MainPresenterImpl(get(), get(), get()) }
}