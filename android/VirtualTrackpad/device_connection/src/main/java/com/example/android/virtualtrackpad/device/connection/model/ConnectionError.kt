package com.example.android.virtualtrackpad.device.connection.model

sealed class ConnectionStatus {
    object Success : ConnectionStatus()
    object Error : ConnectionStatus()
}