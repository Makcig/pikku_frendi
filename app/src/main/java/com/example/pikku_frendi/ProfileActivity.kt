package com.example.pikku_frendi

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import com.example.pikku_frendi.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.bottom_view.*
import android.widget.Toast





class ProfileActivity : BaseActivity(2) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()

        val firebaseAuth = FirebaseAuth.getInstance() // yhteys kirjautumiseen
        var login = firebaseAuth.currentUser // käyttäjätiedot
        val data = FirebaseDatabase.getInstance().reference // yhteys tietokantaan

        // otetaan käyttäjätiedot FireBase tietokannasta
        data.child("users").child(login!!.uid).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(User::class.java)
                edit_txt.setText(userData!!.Name, TextView.BufferType.NORMAL)
                who_text.setText(userData!!.whoami, TextView.BufferType.NORMAL)
                status.setText(userData!!.Status, TextView.BufferType.EDITABLE)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Tietokanta virhe", error.toException())
            }

        })

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val nick: TextView = findViewById<View>(R.id.edit_txt) as TextView
        nick.setOnClickListener {
            val popup = PopupMenu(this@ProfileActivity, nick)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                firebaseAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                true
            })

            popup.show()
        }

        friends.setOnClickListener {
            val intent1 = Intent(this, FriendsActivity::class.java)
            startActivity(intent1)
        }

    }
}

