package com.example.android.virtualtrackpad.repository.device

interface DeviceRepository {

    fun openConnection(address: String)

    fun sendData(data: String)

    fun closeConnection()
}