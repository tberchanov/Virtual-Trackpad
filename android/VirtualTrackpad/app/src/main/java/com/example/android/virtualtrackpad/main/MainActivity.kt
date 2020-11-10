package com.example.android.virtualtrackpad.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.virtualtrackpad.R
import com.example.android.virtualtrackpad.camera.util.CameraPermissionsResolver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var cameraPermissionsResolver: CameraPermissionsResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        CameraPermissionsResolver should init listener for activity result before activity STARTED state
        * */
        cameraPermissionsResolver.init()
    }
}