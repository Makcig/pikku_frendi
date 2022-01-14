package com.example.pikku_frendi

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.pikku_frendi.data.GlideApp
import com.example.pikku_frendi.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1 // kamera varten
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: StorageReference
    private lateinit var userData: User
    private lateinit var database: DatabaseReference
    private lateinit var ImageUri: Uri
    private val SimpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        firebaseAuth = FirebaseAuth.getInstance() // yhteys kirjautumiseen
        firebaseStorage = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference // yhteys tietokantaan
        // otetaan käyttäjätiedot FireBase tietokannasta
        database.child("users").child(firebaseAuth.currentUser!!.uid).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userData = snapshot.getValue(User::class.java)!! // luodaan olio-objekti, mihin tallennetaan data tietokannasta
                username_input.setText(userData!!.Name, TextView.BufferType.EDITABLE)
                bio_input.setText(userData!!.whoami, TextView.BufferType.EDITABLE)
                about_input.setText(userData!!.about, TextView.BufferType.EDITABLE)
                GlideApp.with(this@EditProfileActivity).load(userData.avatar).into(profile_image)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Tietokanta virhe", error.toException())
            }
        })

        close_image.setOnClickListener { finish() }
        done_image.setOnClickListener{ UpdateUserData() }
        change_photo.setOnClickListener{ PicFromCamera() }

    }

    private fun PicFromCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // luotaan intentionin camera varten
        val Image = createImageFile()
        ImageUri = FileProvider.getUriForFile(
            this,
            "com.example.pikku_frendi.fileprovider",
            Image
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri)
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "kamera ei käynistynyt", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat.format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val uid = firebaseAuth.currentUser!!.uid
            val ref = firebaseStorage.child("users/$uid/avatar")
            ref.putFile(ImageUri).addOnCompleteListener{ it ->
                if(it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener{
                        val photoUrl = it.result.toString()
                        database.child("users/$uid/avatar").setValue(photoUrl).addOnCompleteListener{
                            if (it.isSuccessful) {
                                userData = userData.copy(avatar = photoUrl)
                                GlideApp.with(this@EditProfileActivity).load(userData.avatar).into(profile_image)
                            } else {
                                Toast.makeText(this, "Image saving error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else{
                    Toast.makeText(this, "Image load error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun UpdateUserData() {
        val user = User(
            Name = username_input.text.toString(),
            whoami = bio_input.text.toString(),
            about = about_input.text.toString(),
        )
        val updateMap = mutableMapOf<String, Any>()
        updateMap["Name"] = user.Name
        updateMap["whoami"] = user.whoami
        updateMap["about"] = user.about
        if(pass_input.text.toString().isNotEmpty()) {
            val userAuth = firebaseAuth.currentUser
            val newPass = pass_input.text.toString()
            userAuth!!.updatePassword(newPass)
        }
        database.child("users").child(firebaseAuth.currentUser!!.uid).updateChildren(updateMap).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this, ProfileActivity::class.java))
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}