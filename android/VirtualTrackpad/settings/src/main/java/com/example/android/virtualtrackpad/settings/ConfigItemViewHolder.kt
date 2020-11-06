package com.example.android.virtualtrackpad.settings

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.android.virtualtrackpad.settings.model.ConfigItem
import kotlinx.android.synthetic.main.item_config.view.*

internal class ConfigItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(config: ConfigItem) {
        itemView.config_title.setText(config.title)

        when {
            config.textValue != null -> {
                itemView.config_toggle.visibility = View.GONE
                itemView.config_edit.visibility = View.VISIBLE
                itemView.config_edit.setText(config.textValue)
            }
            config.boolValue != null -> {
                itemView.config_toggle.visibility = View.VISIBLE
                itemView.config_edit.visibility = View.GONE
                itemView.config_toggle.isChecked = config.boolValue!!
            }
            else -> {
                throw IllegalStateException("ConfigItem should have value!")
            }
        }

        itemView.config_card.setOnClickListener {
            itemView.config_toggle.toggle()
        }
        itemView.config_toggle.setOnCheckedChangeListener { _, isChecked ->
            config.boolValue = isChecked
        }
        itemView.config_edit.addTextChangedListener {
            config.textValue = it.toString()
        }
    }
}