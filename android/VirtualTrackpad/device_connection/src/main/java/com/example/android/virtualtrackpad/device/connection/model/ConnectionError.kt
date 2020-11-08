package com.example.android.virtualtrackpad.device.connection.model

internal sealed class ConnectionStatus {
    object Success : ConnectionStatus()
    class Error(val device: Device) : ConnectionStatus()
}