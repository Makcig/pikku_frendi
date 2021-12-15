package com.example.pikku_frendi

import android.os.Bundle



class CameraActivity : BaseActivity(1) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        setupBottomNavigation()
    }
}