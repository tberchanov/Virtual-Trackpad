package com.example.android.virtualtrackpad.device.connection.model

internal data class Device(
    val name: String,
    val address: String,
) {
    var isConnecting: Boolean = false
}