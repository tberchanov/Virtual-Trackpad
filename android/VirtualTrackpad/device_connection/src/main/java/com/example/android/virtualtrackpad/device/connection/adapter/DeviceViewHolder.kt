package com.example.android.virtualtrackpad.device.connection.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.android.virtualtrackpad.device.connection.model.Device
import kotlinx.android.synthetic.main.item_device.view.*

internal class DeviceViewHolder(
    view: View,
    private val onDeviceSelected: (Device) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(device: Device) {
        itemView.device_name.text = device.name
        itemView.device_card.setOnClickListener {
            onDeviceSelected(device)
        }
    }

    fun setProgressVisibility(visible: Boolean) {
        itemView.device_progress.isVisible = visible
    }
}