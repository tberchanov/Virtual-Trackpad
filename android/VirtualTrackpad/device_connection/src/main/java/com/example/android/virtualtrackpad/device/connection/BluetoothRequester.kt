package com.example.android.virtualtrackpad.device.connection

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
internal class BluetoothRequester @Inject constructor(
    activity: FragmentActivity
) {

    private var onSuccessHandler: (() -> Unit)? = null

    private var onFailHandler: (() -> Unit)? = null

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val requestBluetoothLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isBluetoothEnabled = result.resultCode == Activity.RESULT_OK

            if (isBluetoothEnabled) {
                onSuccessHandler?.invoke()
            } else {
                onFailHandler?.invoke()
            }
        }

    fun requestBluetooth(
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        onSuccessHandler = onSuccess
        onFailHandler = onFail

        if (bluetoothAdapter.isEnabled) {
            onSuccess.invoke()
        } else {
            requestBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
    }
}