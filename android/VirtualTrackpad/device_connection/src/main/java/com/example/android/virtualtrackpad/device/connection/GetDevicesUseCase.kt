package com.example.android.virtualtrackpad.device.connection

import android.bluetooth.BluetoothAdapter
import com.example.android.virtualtrackpad.device.connection.model.Device
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor() {

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    internal fun execute(): List<Device> {
        return bluetoothAdapter.bondedDevices
            .map {
                Device(it.name, it.address)
            }
    }
}