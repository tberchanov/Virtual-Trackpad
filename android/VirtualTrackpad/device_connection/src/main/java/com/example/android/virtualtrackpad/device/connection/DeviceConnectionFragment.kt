package com.example.android.virtualtrackpad.device.connection

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.virtualtrackpad.device.connection.adapter.DevicesAdapter
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus.Disabled
import com.example.android.virtualtrackpad.device.connection.model.BluetoothStatus.Enabled
import com.example.android.virtualtrackpad.device.connection.model.ConnectionStatus
import com.example.android.virtualtrackpad.device.connection.navigation.DeviceConnectionNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_device_connection.*
import javax.inject.Inject

@AndroidEntryPoint
class DeviceConnectionFragment : Fragment(R.layout.fragment_device_connection) {

    private val viewModel: DeviceConnectionViewModel by viewModels()

    @Inject
    lateinit var navigation: DeviceConnectionNavigation

    @Inject
    internal lateinit var bluetoothRequester: BluetoothRequester

    private val devicesAdapter by lazy {
        DevicesAdapter(
            onDeviceSelected = viewModel::connectToDevice
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        devices_recycler.adapter = devicesAdapter
        turn_on_button.setOnClickListener {
            checkBluetoothAvailability()
        }

        checkBluetoothAvailability()

        with(viewModel) {
            bluetoothStatus.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()
                    ?.let(::handleBluetoothStatus)
            }
            connectionStatus.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()
                    ?.let(::handleConnectionStatus)
            }
            devices.observe(viewLifecycleOwner, devicesAdapter::setItems)
        }
    }

    private fun checkBluetoothAvailability() {
        bluetoothRequester.requestBluetooth(
            { handleBluetoothStatus(Enabled) },
            { handleBluetoothStatus(Disabled) }
        )
    }

    private fun handleConnectionStatus(connectionStatus: ConnectionStatus) {
        when (connectionStatus) {
            ConnectionStatus.Success -> navigation.navigateToCamera()
            ConnectionStatus.Error -> showConnectionDialog()
        }
    }

    private fun handleBluetoothStatus(bluetoothStatus: BluetoothStatus) {
        when (bluetoothStatus) {
            Enabled -> {
                setTurnOnBluetoothMessageVisibility(false)
                viewModel.loadDevices()
            }
            Disabled -> setTurnOnBluetoothMessageVisibility(true)
        }
    }

    private fun showConnectionDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.connection_error_title)
            .setMessage(R.string.connection_error_message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun setTurnOnBluetoothMessageVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        turn_on_label.visibility = visibility
        turn_on_button.visibility = visibility
    }
}