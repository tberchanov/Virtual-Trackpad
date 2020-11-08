package com.example.android.virtualtrackpad.device.connection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.virtualtrackpad.device.connection.data.DeviceRepository
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Error
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Success
import com.example.android.virtualtrackpad.device.connection.model.Device
import com.example.android.virtualtrackpad.device.connection.model.Event

internal class DeviceConnectionViewModel @ViewModelInject constructor(
    private val getDevicesUseCase: GetDevicesUseCase,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    val connectionStatus = MutableLiveData<Event<ConnectionStatus>>()

    val bluetoothStatus = MutableLiveData<Event<BluetoothStatus>>()

    val devices = MutableLiveData<List<Device>>()

    fun loadDevices() {
        devices.value = getDevicesUseCase.execute()
    }

    fun connectToDevice(device: Device) {
        runCatching {
            deviceRepository.openConnection(device.address)
        }.fold(
            { connectionStatus.value = Event(Success) },
            { connectionStatus.value = Event(Error) }
        )
    }
}