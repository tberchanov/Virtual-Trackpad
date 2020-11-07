package com.example.android.virtualtrackpad.device.connection.di

import com.example.android.virtualtrackpad.device.connection.data.BluetoothDeviceRepository
import com.example.android.virtualtrackpad.device.connection.data.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class DeviceRepositoryModule {

    @Provides
    fun provideDeviceRepository(): DeviceRepository = BluetoothDeviceRepository()
}