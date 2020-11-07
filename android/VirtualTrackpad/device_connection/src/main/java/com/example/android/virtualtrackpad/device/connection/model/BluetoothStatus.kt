package com.example.android.virtualtrackpad.device.connection.model

sealed class BluetoothStatus {
    object Enabled : BluetoothStatus()
    object Disabled : BluetoothStatus()
}