package com.example.data

interface DeviceRepository {

    fun waitForConnection()

    fun readData(): String

    fun closeConnection()
}