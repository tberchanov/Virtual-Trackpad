package com.example.android.virtualtrackpad.device.connection.data

import java.io.IOException

interface DeviceRepository {

    @Throws(IOException::class)
    fun openConnection(address: String)

    fun sendData(data: String)

    fun closeConnection()
}