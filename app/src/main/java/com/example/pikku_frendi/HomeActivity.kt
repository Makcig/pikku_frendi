package com.example.pikku_frendi
import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*



class HomeActivity : BaseActivity(0) {
    private lateinit var login: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        scroll_home.scrollTo(0,scroll_home.top)

        login = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if(login.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}
