package com.example.pikku_frendi

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView

abstract class BaseActivity : AppCompatActivity() {
    fun setupBottomNavigation() {
        NavigationBarView.OnItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.menu_home -> MainActivity::class.java
                    R.id.menu_camera -> CameraActivity::class.java
                    R.id.menu_profile -> ProfileActivity::class.java
                    else -> {println("kuka")
                        null}
                }
            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                startActivity(intent)
                true
            } else {
                false
            }
        }
    }
    }