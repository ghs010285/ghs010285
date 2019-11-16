package com.veno_clan.firebaseapp.venocommunity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn.setOnClickListener { login_check() }
        signup_btn.setOnClickListener { signup_event() }
    }

    fun signup_event(){
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun login_check(){
        if(email_edittext.text.toString().isNullOrEmpty() || password_edittext.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.login_fail_null),Toast.LENGTH_SHORT).show()
        } else {
            login()
        }
    }

    fun login(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, getString(R.string.login_complate),Toast.LENGTH_SHORT).show()
//                    moveMainPage(auth?.currentUser)
                } else {
                    Toast.makeText(this, getString(R.string.accoint_null),Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            Toast.makeText(this, "WelCome!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }
}
