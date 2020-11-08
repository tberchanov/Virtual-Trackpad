fun main() {
    val deviceRepository: DeviceRepository = BluetoothDeviceRepository()

    deviceRepository.waitForConnection()

    while (true) {
        val receivedData = deviceRepository.readData()

        println("Received bluetooth data: $receivedData")
    }

    deviceRepository.closeConnection()
}