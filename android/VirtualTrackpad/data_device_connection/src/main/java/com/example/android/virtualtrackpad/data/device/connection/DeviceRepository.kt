package com.example.android.virtualtrackpad.data.device.connection

import java.io.IOException

interface DeviceRepository {

    @Throws(IOException::class)
    fun openConnection(address: String)

    @Throws(IOException::class)
    fun sendData(data: String)

    fun sendBytes(byteArray: ByteArray)

    fun closeConnection()
}