package com.example.android.virtualtrackpad.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModels()

    private val adapter = ConfigItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_recycler.adapter = adapter

        viewModel.loadConfigItems()
            .observe(viewLifecycleOwner) {
                adapter.setItems(it)
            }

        save_settings_button.setOnClickListener {
            viewModel.saveConfigItems(adapter.configItems)
                .observe(viewLifecycleOwner) {
                    if (it.isFailure) {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.invalid_settings_value)
                            .setPositiveButton(R.string.ok, null)
                            .show()
                    } else {
                        findNavController().popBackStack()
                    }
                }
        }
    }
}