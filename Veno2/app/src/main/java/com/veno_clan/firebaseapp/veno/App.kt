package com.veno_clan.firebaseapp.veno

import android.app.Application

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import java.util.HashMap

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val defaultValue = HashMap<String, Any>()
        defaultValue[UpdateHelper.KEY_UPDATE_TEXT] = false
        defaultValue[UpdateHelper.KEY_UPDATE_URL] = "https://naver.com"
        defaultValue[UpdateHelper.KEY_UPDATE_VERSION] = "1.0"

        remoteConfig.setDefaults(defaultValue)
        remoteConfig.fetch(5)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    remoteConfig.activateFetched()
                }
            }
    }
}
