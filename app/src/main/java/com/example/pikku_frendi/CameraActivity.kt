package com.example.pikku_frendi

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*



class CameraActivity : BaseActivity(1) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        back_image.setOnClickListener{
            finish()
        }
    }
}