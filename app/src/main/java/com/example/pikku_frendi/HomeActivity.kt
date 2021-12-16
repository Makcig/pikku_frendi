package com.example.pikku_frendi


import android.os.Bundle

import kotlinx.android.synthetic.main.bottom_view.*


class HomeActivity : BaseActivity(0) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
    }
}


