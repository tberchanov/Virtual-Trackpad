package com.example.android.virtualtrackpad.device.connection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.virtualtrackpad.device.connection.data.DeviceRepository
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus.Disabled
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus.Enabled
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Error
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Success
import com.example.android.virtualtrackpad.device.connection.model.Device
import com.example.android.virtualtrackpad.device.connection.model.Event

class DeviceConnectionViewModel @ViewModelInject constructor(
    private val getDevicesUseCase: GetDevicesUseCase,
    private val deviceRepository: DeviceRepository,
    private val bluetoothRequester: BluetoothRequester
) : ViewModel() {

    internal val connectionStatus = MutableLiveData<Event<ConnectionStatus>>()

    internal val bluetoothStatus = MutableLiveData<Event<BluetoothStatus>>()

    internal val devices = MutableLiveData<List<Device>>()

    internal fun loadDevices() {
        devices.value = getDevicesUseCase.execute()
    }

    internal fun connectToDevice(device: Device) {
        runCatching {
            deviceRepository.openConnection(device.address)
        }.fold(
            { connectionStatus.value = Event(Success) },
            { connectionStatus.value = Event(Error) }
        )
    }

    internal fun checkBluetoothAvailability() {
        bluetoothRequester.requestBluetooth(
            { bluetoothStatus.value = Event(Enabled) },
            { bluetoothStatus.value = Event(Disabled) }
        )
    }
}