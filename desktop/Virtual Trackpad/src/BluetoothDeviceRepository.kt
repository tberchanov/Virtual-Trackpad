import java.io.InputStream
import javax.bluetooth.UUID
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnection
import javax.microedition.io.StreamConnectionNotifier

// TODO need to add error handling and logging
class BluetoothDeviceRepository : DeviceRepository {

    /*
    * `streamConnection` - object representing connection with device.
    * Following data can be received:
    * - RemoteDevice.getRemoteDevice(streamConnection)
    * - streamConnection.openInputStream()
    * - streamConnection.openOutputStream()
    * */
    private var streamConnection: StreamConnection? = null

    private var deviceInputStream: InputStream? = null

    override fun waitForConnection() {
        val streamConnNotifier = Connector.open(connectionString) as StreamConnectionNotifier
        streamConnection = streamConnNotifier.acceptAndOpen()
        deviceInputStream = streamConnection?.openInputStream()
    }

    override fun readData(): String {
        return deviceInputStream?.readBytes()?.let { bytes ->
            String(bytes)
        } ?: throw IllegalStateException("Null bytes can no be read")
    }

    override fun closeConnection() {
        deviceInputStream?.close()
        deviceInputStream = null

        streamConnection?.close()
        streamConnection = null
    }

    companion object {

        private const val connectionName = "Virtual Trackpad Server"

        // TODO probably should be secured
        private val uuid = UUID("1101", true)

        // TODO probably should be secured
        private val connectionString = "btspp://localhost:$uuid;name=$connectionName"
    }
}