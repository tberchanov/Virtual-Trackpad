package com.example.di

import com.example.data.BluetoothDeviceRepository
import com.example.data.DeviceRepository
import org.koin.dsl.module

val dataModule = module {
    single<DeviceRepository> { BluetoothDeviceRepository() }
}