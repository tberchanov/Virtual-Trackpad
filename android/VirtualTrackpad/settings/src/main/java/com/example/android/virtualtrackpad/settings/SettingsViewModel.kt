package com.example.android.virtualtrackpad.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.virtualtrackpad.settings.model.ConfigItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    private val loadConfigItemsUseCase: LoadConfigItemsUseCase,
    private val saveConfigItemsUseCase: SaveConfigItemsUseCase,
) : ViewModel() {

    internal fun loadConfigItems(): LiveData<List<ConfigItem>> {
        val configItems = MutableLiveData<List<ConfigItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            loadConfigItemsUseCase.execute()
                .let(configItems::postValue)
        }
        return configItems
    }

    internal fun saveConfigItems(configItems: List<ConfigItem>): LiveData<Result<Unit>> {
        val result = MutableLiveData<Result<Unit>>()
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                saveConfigItemsUseCase.execute(configItems)
            }.let {
                result.postValue(it)
            }
        }
        return result
    }
}