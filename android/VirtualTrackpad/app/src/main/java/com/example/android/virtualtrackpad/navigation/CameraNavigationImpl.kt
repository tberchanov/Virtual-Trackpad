package com.example.android.virtualtrackpad.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.android.virtualtrackpad.R
import com.example.android.virtualtrackpad.camera.navigation.CameraNavigation

class CameraNavigationImpl(
    activity: FragmentActivity
) : CameraNavigation {

    private val navController = activity.findNavController(R.id.nav_host_fragment)

    override fun navigateToSettings() {
        navController.navigate(R.id.action_camera_fragment_to_settings_fragment)
    }

    override fun back() {
        navController.popBackStack()
    }
}