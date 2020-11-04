package com.example.android.virtualtrackpad.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.virtualtrackpad.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // TODO setup DI, should not be dependency on implementation
    private val mainNavigation: MainNavigation by lazy {
        MainNavigationImpl(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainNavigation.showCameraScreen()
    }
}