package com.example.pikku_frendi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.bottom_view.*


class ProfileActivity : BaseActivity(2) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val nick: TextView = findViewById<View>(R.id.nickname) as TextView
        nick.setOnClickListener {
            val popup = PopupMenu(this@ProfileActivity, nick)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.show()
        }

        friends.setOnClickListener {
            val intent1 = Intent(this, FriendsActivity::class.java)
            startActivity(intent1)
        }
    }
}

