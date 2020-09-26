package com.example.android.virtualtrackpad.main

import androidx.fragment.app.FragmentManager
import com.example.android.virtualtrackpad.R
import com.example.android.virtualtrackpad.camera.CameraFragment

class MainNavigationImpl(
    private val fragmentManager: FragmentManager
) : MainNavigation {

    override fun showCameraScreen() {
        fragmentManager.beginTransaction()
            .add(R.id.mainContainer, CameraFragment())
            .commit()
    }
}