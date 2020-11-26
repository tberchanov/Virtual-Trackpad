package com.example.data

import com.example.exceptions.ConnectionCorruptedException
import java.io.InputStream
import javax.bluetooth.UUID
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnection
import javax.microedition.io.StreamConnectionNotifier

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

    private val inputBuffer = ByteArray(DEFAULT_INPUT_BUFFER_SIZE)

    private lateinit var streamConnNotifier: StreamConnectionNotifier

    override fun waitForConnection() {
        streamConnection = getStreamConnNotifier().acceptAndOpen()
        deviceInputStream = streamConnection?.openInputStream()
    }

    private fun getStreamConnNotifier(): StreamConnectionNotifier {
        if (!::streamConnNotifier.isInitialized) {
            streamConnNotifier = Connector.open(connectionString) as StreamConnectionNotifier
        }
        return streamConnNotifier
    }

    override fun readData(): String {
        val receivedBytesAmount = deviceInputStream?.read(inputBuffer)

        if (receivedBytesAmount == NO_MORE_DATA) {
            throw ConnectionCorruptedException()
        }

        return String(inputBuffer.getNotEmptyBytes())
    }

    private fun ByteArray.getNotEmptyBytes(): ByteArray {
        val lastNotEmptyIndex = getLastNotEmptyIndex(this)
        return if (lastNotEmptyIndex == -1) {
            this
        } else {
            this.copyOf(lastNotEmptyIndex.inc())
        }
    }

    private fun getLastNotEmptyIndex(byteArray: ByteArray): Int {
        for (i in (byteArray.size - 1) downTo 0) {
            if (byteArray[i] != 0.toByte()) {
                return i
            }
        }
        return -1
    }

    override fun closeConnection() {
        deviceInputStream?.close()
        deviceInputStream = null

        streamConnection?.close()
        streamConnection = null
    }

    companion object {

        private const val NO_MORE_DATA = -1

        private const val DEFAULT_INPUT_BUFFER_SIZE = 80

        private const val connectionName = "Virtual Trackpad Server"

        // TODO probably should be secured
        private val uuid = UUID("1101", true)

        // TODO probably should be secured
        private val connectionString = "btspp://localhost:$uuid;name=$connectionName"
    }
}