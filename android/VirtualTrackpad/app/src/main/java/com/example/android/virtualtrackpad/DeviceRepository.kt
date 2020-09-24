package com.example.android.virtualtrackpad

interface DeviceRepository {

    fun openConnection(address: String)

    fun sendData(data: String)

    fun closeConnection()
}