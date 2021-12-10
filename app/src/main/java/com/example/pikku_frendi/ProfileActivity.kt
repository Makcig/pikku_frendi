package com.example.pikku_frendi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*



class ProfileActivity : BaseActivity() {
    private val tag = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "onCreate")
        setupBottomNavigation()
    }
}