package com.example.android.virtualtrackpad

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter?.isEnabled == true) {
            testBluetoothDeviceRepository()
        } else {
            startActivityForResult(
                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                REQUEST_ENABLE_BT
            )
        }
    }

    private fun testBluetoothDeviceRepository() {
        val deviceRepository: DeviceRepository = BluetoothDeviceRepository()

        deviceRepository.openConnection("48:F1:7F:EC:C2:5E")

        deviceRepository.sendData("Hello from Android!")

        deviceRepository.closeConnection()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ENABLE_BT -> Log.e(TAG, "on request bt result: $requestCode $data")
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_ENABLE_BT = 1
    }
}