package com.example.pikku_frendi

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*
import org.w3c.dom.Comment
import java.sql.Types.TIMESTAMP
import java.util.*


class CameraActivity : BaseActivity(1) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        back_image.setOnClickListener{
            finish()
        }
    }
}
