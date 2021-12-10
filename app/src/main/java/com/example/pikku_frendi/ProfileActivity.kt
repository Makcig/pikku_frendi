package com.example.pikku_frendi

import android.os.Bundle
import android.util.Log




class ProfileActivity : BaseActivity() {
    private val tag = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(tag, "onCreate")
        setupBottomNavigation()
    }
}