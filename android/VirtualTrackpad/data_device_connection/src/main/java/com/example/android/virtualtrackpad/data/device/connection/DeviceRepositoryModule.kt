package com.example.android.virtualtrackpad.data.device.connection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DeviceRepositoryModule {

    @Singleton
    @Provides
    fun provideDeviceRepository(): DeviceRepository =
        BluetoothDeviceRepository()
}