package com.example.android.virtualtrackpad.device.connection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.virtualtrackpad.device.connection.R
import com.example.android.virtualtrackpad.device.connection.model.Device

internal class DevicesAdapter(
    private val devices: MutableList<Device> = mutableListOf(),
    private val onDeviceSelected: (Device) -> Unit
) : RecyclerView.Adapter<DeviceViewHolder>() {

    fun setItems(devices: List<Device>) {
        this.devices.clear()
        this.devices.addAll(devices)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device, parent, false).let { root ->
                DeviceViewHolder(root, onDeviceSelected)
            }
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun onBindViewHolder(
        holder: DeviceViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(devices[position])
        holder.setProgressVisibility(payloads.contains(PAYLOAD_DEVICE_LOADING))
    }

    override fun getItemCount() = devices.size

    fun showItemProgress(device: Device) {
        setPayloadToDeviceItem(device, PAYLOAD_DEVICE_LOADING)
    }

    fun hideItemProgress(device: Device) {
        setPayloadToDeviceItem(device, PAYLOAD_DEVICE_NOT_LOADING)
    }

    private fun setPayloadToDeviceItem(device: Device, payload: Any) {
        devices.indexOf(device)
            .takeIf { it != -1 }
            ?.let { index ->
                notifyItemChanged(index, payload)
            }
    }

    companion object {
        private const val PAYLOAD_DEVICE_LOADING = 1
        private const val PAYLOAD_DEVICE_NOT_LOADING = 2
    }
}