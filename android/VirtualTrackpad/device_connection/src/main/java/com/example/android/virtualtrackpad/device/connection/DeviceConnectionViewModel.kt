package com.example.android.virtualtrackpad.device.connection

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.virtualtrackpad.data.device.connection.DeviceRepository
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Error
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus.Success
import com.example.android.virtualtrackpad.device.connection.model.Device
import com.example.android.virtualtrackpad.device.connection.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                deviceRepository.openConnection(device.address)
            }.fold(
                { connectionStatus.postValue(Event(Success)) },
                {
                    Log.e("DeviceConnection", "connectToDevice error:", it)
                    connectionStatus.postValue(Event(Error(device)))
                }
            )
        }
    }
}