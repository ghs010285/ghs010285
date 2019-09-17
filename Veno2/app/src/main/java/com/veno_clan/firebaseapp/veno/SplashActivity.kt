package com.veno_clan.firebaseapp.veno

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import com.bumptech.glide.Glide.init
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class SplashActivity : AppCompatActivity(){


    private lateinit var remoteConfig: FirebaseRemoteConfig
    private val FIREBASE_FATCH_TIME: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initRemoteConfig()
    }















    /*override fun onUpdateCheckListner(urlApp: String) {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("새로운 앱 버전")
            .setMessage("새로운 앱 버전이 있습니다. 업데이트를 해 주세요")
            .setPositiveButton("업데이트") { dialogInterface, i ->
                Toast.makeText(this, ""+urlApp, Toast.LENGTH_SHORT).show()
            }.setNegativeButton("취소") { dialogInterface, i -> dialogInterface.dismiss() }.create()
        alertDialog.show()
    }*/


    fun initRemoteConfig(){
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
        remoteConfig.setConfigSettings(configSettings)
        remoteConfig.setDefaults(R.xml.remote_default)
//        remoteConfig.setDefaults(R.xml.app_update_versions)
        fetchAction()
    }
    fun fetchAction() {
        remoteConfig.fetch(FIREBASE_FATCH_TIME)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    remoteConfig.activateFetched()
                } else {

                }
                displayWelcomeMessage()

            }
    }

     fun displayWelcomeMessage() {
         val caps: Boolean = remoteConfig.getBoolean("welcome_message_caps")
         val splashMesseage: String = remoteConfig.getString("welcome_message")

         val builder: AlertDialog.Builder = AlertDialog.Builder(this)

         if (caps) {
             builder.setMessage(splashMesseage)
                 .setCancelable(false)
                 .setPositiveButton("확인") { dialog, which -> finish() }
             builder.create().show()
         }else{
             startActivity(Intent(this, LoginActivity::class.java))
             finish()
         }

     }

}

