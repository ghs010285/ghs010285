package com.veno_clan.firebaseapp.veno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

        email_login_button.setOnClickListener { emailLogin() }
        google_sign_in_button.setOnClickListener { google_btn() }
        facebook_login_button.setOnClickListener { facebook_btn() }
        twitter_login_button.setOnClickListener { twitter_btn() }
    }
    fun moveMainPage(user : FirebaseUser?){
        if(user != null){
            Toast.makeText(this, getString(R.string.signin_complate), Toast.LENGTH_SHORT).show()
            startActivity(Intent (this, MainActivity::class.java))
            finish()
        }
    }

    fun google_btn(){
        Toast.makeText(this, "존재하지 않는 이벤트입니다. 개발자에게 문의 해 주세요", Toast.LENGTH_SHORT).show()
    }

    fun facebook_btn(){
        Toast.makeText(this, "존재하지 않는 이벤트입니다. 개발자에게 문의 해 주세요", Toast.LENGTH_SHORT).show()
    }
    fun twitter_btn(){
        Toast.makeText(this, "존재하지 않는 이벤트입니다. 개발자에게 문의 해 주세요", Toast.LENGTH_SHORT).show()
    }

    fun createAndLoginEmail(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener { task -> progress_bar.visibility  = View.GONE
            if(task.isSuccessful) {
                Toast.makeText(this, getString(R.string.signup_complate),Toast.LENGTH_SHORT).show()
                moveMainPage(auth?.currentUser)
            } else if (task.exception?.message.isNullOrEmpty()) {
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            } else{
                signinEmail()
            }
          }
    }

    fun emailLogin(){
        if(email_edittext.text.toString().isNullOrEmpty() || password_edittext.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.sign_out_fail_null),Toast.LENGTH_SHORT).show()
        } else {
            progress_bar.visibility = View.VISIBLE
            createAndLoginEmail()
        }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener { task -> progress_bar.visibility = View.GONE
            if(task.isSuccessful){
                moveMainPage(auth?.currentUser)
            }else{
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }}
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }
}
