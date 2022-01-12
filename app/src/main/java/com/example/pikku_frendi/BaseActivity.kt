package com.example.pikku_frendi

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bottom_view.*

abstract class BaseActivity (private val navNumber: Int): AppCompatActivity() {
    private val tag = "BaseActivity"
    fun setupBottomNavigation() {   //activities navigointi painamalla menu-icon (kaikilla ikkunoilla)
        bottom_view.setOnItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.menu_home -> HomeActivity::class.java
                    R.id.menu_camera -> CameraActivity::class.java
                    R.id.menu_profile -> ProfileActivity::class.java
                    else -> {Log.e(tag, "error")
                        null
                }
        }
            if (nextActivity != null) { //jos seuraava activity ei ole nolla, silloin navigointi onnistuu
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION //navigointi ilman "hypyä"
                startActivity(intent)
                overridePendingTransition(0,0) //navigointi ilman "hypyä"
                true
            } else {
                false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        if (bottom_view != null) {
            bottom_view.menu.getItem(navNumber).isChecked = true //valittu menu-icon on actiivinen
        }
    }
    }