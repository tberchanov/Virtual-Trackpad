package com.example.android.virtualtrackpad.repository.device

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.io.OutputStream
import java.util.*

class BluetoothDeviceRepository : DeviceRepository {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var bluetoothSocket: BluetoothSocket? = null

    private var bluetoothOutputStream: OutputStream? = null

    override fun openConnection(address: String) {
        try {
            bluetoothSocket = bluetoothAdapter
                ?.getRemoteDevice(address)
                ?.createRfcommSocketToServiceRecord(BLUETOOTH_UUID)
        } catch (e: IOException) {
            // TODO exception should be delivered to the consumer
            return
        }

        try {
            bluetoothSocket?.connect()
        } catch (e: IOException) {
            // TODO exception should be delivered to the consumer
            return
        }

        try {
            bluetoothOutputStream = bluetoothSocket?.outputStream
        } catch (e: IOException) {
            // TODO exception should be delivered to the consumer
            return
        }
    }

    override fun sendData(data: String) {
        try {
            // TODO receive writable data from argument and convert it to the bytes
            bluetoothOutputStream?.write(data.toByteArray())
        } catch (e: IOException) {
            // TODO exception should be delivered to the consumer
            return
        }
    }

    override fun closeConnection() {
        try {
            bluetoothOutputStream?.flush()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            // TODO exception should be delivered to the consumer
            return
        }
        bluetoothOutputStream = null
        bluetoothSocket = null
    }

    companion object {
        // TODO probably should be secured
        private val BLUETOOTH_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}