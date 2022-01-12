package com.example.pikku_frendi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        KeyboardVisibilityEvent.setEventListener(this, this)
        login_input.setOnClickListener(this)
        sign_btn.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if(isKeyboardOpen){
            scroll.scrollTo(0,login_label.top)
        }else{
            scroll.scrollTo(0,scroll.top)
        }
    }

    override fun onClick(view: View) {
        if(login_input.text.isNotEmpty() && password_input.text.isNotEmpty()) { // tarkistetaan että kentät ei ole tyhät
            val mail = login_input.text.toString()
            val pass = password_input.text.toString()
            firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener{
                if(it.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java)) // jos kirjautumis onnistui, siiretään HomeActivity:n
                    finish()
                }
                else{
                    Toast.makeText(this, "Login or Password is wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}