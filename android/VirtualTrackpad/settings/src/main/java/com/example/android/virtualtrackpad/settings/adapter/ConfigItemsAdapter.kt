package com.example.android.virtualtrackpad.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.virtualtrackpad.settings.R
import com.example.android.virtualtrackpad.settings.model.ConfigItem

internal class ConfigItemsAdapter(
    private val configItems: MutableList<ConfigItem> = mutableListOf()
) : RecyclerView.Adapter<ConfigItemViewHolder>() {

    val configs: List<ConfigItem> = configItems

    fun setItems(configItems: List<ConfigItem>) {
        this.configItems.clear()
        this.configItems.addAll(configItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigItemViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_config, parent, false).let { root ->
                ConfigItemViewHolder(root)
            }
    }

    override fun onBindViewHolder(holder: ConfigItemViewHolder, position: Int) {
        holder.bind(configItems[position])
    }

    override fun getItemCount() = configItems.size
}