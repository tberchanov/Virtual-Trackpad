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

    override fun getItemCount() = devices.size
}