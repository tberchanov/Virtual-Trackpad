package com.example.android.virtualtrackpad.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.virtualtrackpad.settings.adapter.ConfigItemsAdapter
import com.example.android.virtualtrackpad.settings.navigation.SettingsNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var navigation: SettingsNavigation

    private val viewModel: SettingsViewModel by viewModels()

    private val adapter = ConfigItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_recycler.adapter = adapter

        viewModel.configItems.observe(viewLifecycleOwner, adapter::setItems)
        viewModel.saveResult.observe(viewLifecycleOwner) {
            if (it.isFailure) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.invalid_settings_value)
                    .setPositiveButton(R.string.ok, null)
                    .show()
            } else {
                navigation.back()
            }
        }

        viewModel.loadConfigItems()

        save_settings_button.setOnClickListener {
            viewModel.saveConfigItems(adapter.configs)
        }
    }
}