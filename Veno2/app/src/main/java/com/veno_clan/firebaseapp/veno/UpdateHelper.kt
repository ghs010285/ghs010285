package com.veno_clan.firebaseapp.veno

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class UpdateHelper(
    private val context: Context,
    private val onUpdateCheckLIstner: OnUpdateCheckLIstner?
) {

    interface OnUpdateCheckLIstner {
        fun onUpdateCheckListner(urlApp: String)
    }

    fun check() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        if (remoteConfig.getBoolean(KEY_UPDATE_TEXT)) {
            val CurrentVersion = remoteConfig.getString(KEY_UPDATE_VERSION)
            val appVersion = getAppVersion(context)
            val updateURL = remoteConfig.getString(KEY_UPDATE_URL)

            if (!TextUtils.equals(CurrentVersion, appVersion) && onUpdateCheckLIstner != null)
                onUpdateCheckLIstner.onUpdateCheckListner(updateURL)
        }
    }


    private fun getAppVersion(context: Context): String {

        var result = ""
        try {
            result = context.packageManager.getPackageInfo(context.packageName, 0)
                .versionName
            result = result.replace("[a-zA-z]] |-".toRegex(), "")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return result
    }

    class Builder(private val context: Context) {
        private val onUpdateCheckLIstner: OnUpdateCheckLIstner? = null

        fun onUpdateCheck(onUpdateCheckLIstner: OnUpdateCheckLIstner): Builder {
            return this
        }

        fun builder(): UpdateHelper {
            return UpdateHelper(context, onUpdateCheckLIstner)
        }

        fun check(): UpdateHelper {
            val updateHelper = builder()
            updateHelper.check()

            return updateHelper
        }
    }

    companion object {

        var KEY_UPDATE_TEXT = "update_check"
        var KEY_UPDATE_VERSION = "update_version"
        var KEY_UPDATE_URL = "update_url"

        fun with(context: Context): Builder {
            return Builder(context)
        }
    }
}
