package com.example.android.virtualtrackpad.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.android.virtualtrackpad.R
import com.example.android.virtualtrackpad.settings.navigation.SettingsNavigation

class SettingsNavigationImpl(
    activity: FragmentActivity
) : SettingsNavigation {

    private val navController = activity.findNavController(R.id.nav_host_fragment)

    override fun back() {
        navController.popBackStack()
    }
}