package com.veno_clan.firebaseapp.veno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.email_edittext
import kotlinx.android.synthetic.main.activity_sign_up.password_edittext
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar

class SignUpActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        signup_email.setOnClickListener { createEmail() }
    }

    fun jungbo() {
        startActivity(Intent(this, JungboActivity::class.java))
    }


    fun createEmail(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener { task -> progress_bar.visibility  = View.GONE
                if(email_edittext.text.toString().isNullOrEmpty() || password_edittext.text.toString().isNullOrEmpty() || nikname_edittext.text.toString().isNullOrEmpty()){
                    progress_bar.visibility = View.VISIBLE
                    Toast.makeText(this, getString(R.string.signup_complate),Toast.LENGTH_SHORT).show()
                    moveLoginPage()
                } else if(task.isSuccessful){
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }/* else if (task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }*/ else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun moveLoginPage(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}

