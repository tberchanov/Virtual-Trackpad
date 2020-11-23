package com.example.android.virtualtrackpad.camera.usecase

import com.example.android.virtualtrackpad.data.device.connection.DeviceRepository
import javax.inject.Inject

class CloseConnectionUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    fun execute() {
        deviceRepository.closeConnection()
    }
}