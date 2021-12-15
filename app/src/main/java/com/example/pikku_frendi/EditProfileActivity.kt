package com.example.pikku_frendi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        close_image.setOnClickListener{
            finish()
        }
        change_photo.setOnClickListener{takePhoto()}
    }

    private fun takePhoto() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
       if (intent.resolveActivity(packageManager) != null){


       }
    }
}