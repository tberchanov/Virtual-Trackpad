package com.example.android.virtualtrackpad.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.android.virtualtrackpad.R
import com.example.android.virtualtrackpad.device.connection.navigation.DeviceConnectionNavigation

class DeviceConnectionNavigationImpl(
    activity: FragmentActivity
) : DeviceConnectionNavigation {

    private val navController = activity.findNavController(R.id.nav_host_fragment)

    override fun navigateToCamera() {
        navController.navigate(R.id.action_connection_fragment_to_camera_fragment)
    }
}