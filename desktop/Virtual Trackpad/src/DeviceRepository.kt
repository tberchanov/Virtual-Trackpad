interface DeviceRepository {

    fun waitForConnection()

    fun readData(): String

    fun closeConnection()
}