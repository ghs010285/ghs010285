package com.veno_clan.firebaseapp.veno

import android.app.Application
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class BaseApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        remoteConfigInit()
    }

    private fun remoteConfigInit(){
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()

        val remoteConfigDefaults = HashMap<String, Any>()
        remoteConfigDefaults["update_version"] = "1.0"
        remoteConfigDefaults["update_url"] = "https://naver.com"

        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettings(configSettings)
            setDefaults(remoteConfigDefaults)
            fetch(60).addOnCompleteListener { task: Task<Void> ->
                if (task.isSuccessful){
                    activateFetched()
                }
            }
        }

    }



}