package com.example.android.virtualtrackpad.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.virtualtrackpad.settings.model.ConfigItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SettingsViewModel @ViewModelInject constructor(
    private val loadConfigItemsUseCase: LoadConfigItemsUseCase,
    private val saveConfigItemsUseCase: SaveConfigItemsUseCase,
) : ViewModel() {

    val saveResult = MutableLiveData<Result<Unit>>()

    val configItems = MutableLiveData<List<ConfigItem>>()

    fun loadConfigItems() {
        viewModelScope.launch(Dispatchers.IO) {
            loadConfigItemsUseCase.execute()
                .let(configItems::postValue)
        }
    }

    fun saveConfigItems(configItems: List<ConfigItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                saveConfigItemsUseCase.execute(configItems)
            }.let {
                saveResult.postValue(it)
            }
        }
    }
}