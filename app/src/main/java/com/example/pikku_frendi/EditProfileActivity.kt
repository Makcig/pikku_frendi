package com.example.pikku_frendi

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.pikku_frendi.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_profile.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userData: User
    private lateinit var data: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        firebaseAuth = FirebaseAuth.getInstance() // yhteys kirjautumiseen
        data = FirebaseDatabase.getInstance().reference // yhteys tietokantaan
        // otetaan käyttäjätiedot FireBase tietokannasta
        data.child("users").child(firebaseAuth.currentUser!!.uid).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userData = snapshot.getValue(User::class.java)!! // luodaan olio-objekti, mihin tallennetaan data tietokannasta
                username_input.setText(userData!!.Name, TextView.BufferType.EDITABLE)
                bio_input.setText(userData!!.whoami, TextView.BufferType.EDITABLE)
                about_input.setText(userData!!.about, TextView.BufferType.EDITABLE)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "Tietokanta virhe", error.toException())
            }
        })

        close_image.setOnClickListener { finish() }
        done_image.setOnClickListener{ UpdateUserData() }

    }

    private fun UpdateUserData() {
        val user = User(
            Name = username_input.text.toString(),
            whoami = bio_input.text.toString(),
            about = about_input.text.toString(),
            Status = userData.Status
        )
        val updateMap = mutableMapOf<String, Any>()
        updateMap["Name"] = user.Name
        updateMap["whoami"] = user.whoami
        updateMap["about"] = user.about
        updateMap["Status"] = user.Status
        if(pass_input.text.toString().isNotEmpty()) {
            val userAuth = firebaseAuth.currentUser
            val newPass = pass_input.text.toString()
            userAuth!!.updatePassword(newPass)
        }
        data.child("users").child(firebaseAuth.currentUser!!.uid).updateChildren(updateMap).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this, ProfileActivity::class.java))
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}