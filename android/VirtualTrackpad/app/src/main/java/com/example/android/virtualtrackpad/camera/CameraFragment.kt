package com.example.android.virtualtrackpad.camera

import android.os.Bundle
import android.view.View
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.virtualtrackpad.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_camera.*

@AndroidEntryPoint
class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val viewModel: CameraViewModel by viewModels()

    private val cameraPermissionsResolver by lazy { CameraPermissionsResolver(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detectionResult.observe(viewLifecycleOwner, result_overlay::updateResults)

        cameraPermissionsResolver.checkAndRequestPermissionsIfNeeded(
            onSuccess = {
                viewModel.getProcessCameraProvider(requireContext(), ::bindCamera)
            },
            onFail = ::showSnackbar
        )
    }

    private fun bindCamera(cameraProvider: ProcessCameraProvider) {

        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(
            viewLifecycleOwner,
            viewModel.cameraSelector,
            *getCameraUseCases().toTypedArray()
        )
    }

    private fun getCameraUseCases() = mutableListOf<UseCase>(
        viewModel.getImageAnalysis()
    ).also {
        val preview = viewModel.getPreview()
        if (preview != null) {
            it.add(preview)
            preview.setSurfaceProvider(preview_view.surfaceProvider)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(root_container, message, Snackbar.LENGTH_LONG).show()
    }
}