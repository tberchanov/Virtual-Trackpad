fun main() {
    val deviceRepository: DeviceRepository = BluetoothDeviceRepository()

    deviceRepository.waitForConnection()

    val receivedData = deviceRepository.readData()

    println("Received bluetooth data: $receivedData")

    deviceRepository.closeConnection()
}