package com.example.android.virtualtrackpad.di

import androidx.fragment.app.FragmentActivity
import com.example.android.virtualtrackpad.device.connection.navigation.DeviceConnectionNavigation
import com.example.android.virtualtrackpad.navigation.DeviceConnectionNavigationImpl
import com.example.android.virtualtrackpad.navigation.SettingsNavigationImpl
import com.example.android.virtualtrackpad.settings.navigation.SettingsNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun provideDeviceConnectionNavigation(
        fragmentActivity: FragmentActivity
    ): DeviceConnectionNavigation = DeviceConnectionNavigationImpl(fragmentActivity)

    @Provides
    fun provideSettingsNavigation(
        fragmentActivity: FragmentActivity
    ): SettingsNavigation = SettingsNavigationImpl(fragmentActivity)
}