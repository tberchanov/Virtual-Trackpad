package com.example.android.virtualtrackpad.settings.model

import androidx.annotation.StringRes

internal data class ConfigItem(
    val type: ConfigType,
    @StringRes val title: Int,
    var boolValue: Boolean? = null,
    var textValue: String? = null
)