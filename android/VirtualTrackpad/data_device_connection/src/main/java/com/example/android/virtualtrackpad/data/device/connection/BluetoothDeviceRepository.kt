package com.example.android.virtualtrackpad.data.device.connection

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.io.OutputStream
import java.util.*

internal class BluetoothDeviceRepository : DeviceRepository {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var bluetoothSocket: BluetoothSocket? = null

    private var bluetoothOutputStream: OutputStream? = null

    private val outputBuffer = ByteArray(DEFAULT_OUTPUT_BUFFER_SIZE)

    @Throws(IOException::class)
    override fun openConnection(address: String) {
        bluetoothSocket = bluetoothAdapter
            ?.getRemoteDevice(address)
            ?.createRfcommSocketToServiceRecord(BLUETOOTH_UUID)
        bluetoothSocket?.connect()
        bluetoothOutputStream = bluetoothSocket?.outputStream
    }

    @Throws(IOException::class)
    override fun sendData(data: String) {
        val dataBytes = data.toByteArray()
        dataBytes.copyInto(outputBuffer)
        outputBuffer.clean(dataBytes.size)

        bluetoothOutputStream?.write(outputBuffer)
        bluetoothOutputStream?.flush()
    }

    private fun ByteArray.clean(startOffset: Int = 0) {
        (startOffset until size).forEach { i ->
            this[i] = 0
        }
    }

    override fun sendBytes(byteArray: ByteArray) {
        bluetoothOutputStream?.write(byteArray)
        bluetoothOutputStream?.flush()
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

        private const val DEFAULT_OUTPUT_BUFFER_SIZE = 80

        // TODO probably should be secured
        private val BLUETOOTH_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}