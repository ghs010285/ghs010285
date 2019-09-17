package com.veno_clan.firebaseapp.veno.util

import com.veno_clan.firebaseapp.veno.model.PushDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.okhttp.*
import java.io.IOException
import com.squareup.okhttp.RequestBody.create as create1

class FcmPush() {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val url = "https://fcm.googleapis.com/fcm/send"
    val serverKey = "AAAAa_cebZQ:APA91bGO6rsmOfBrkRw0WJwKH13Fc03oxwEy-OTYxRNVPbDVMyjsJj35vc4j9baOpMNaQ7Zf6b6Xb-2OWGVW6x0b4MXJq56iIHCxDGIt0DfecpkS3N3Gzv2jGtLM2fzgx5zXF_87Urub"

    var okHttpClient: OkHttpClient? = null
    var gson: Gson? = null
    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationUid: String, title: String, message: String) {
        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var token = task.result?.get("pushtoken")?.toString()
                println(token)
                var pushDTO = PushDTO()
                pushDTO.to = token
                pushDTO.notification?.title = title
                pushDTO.notification?.body = message

                var body = create1(JSON, gson?.toJson(pushDTO))
                var request = Request
                    .Builder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "key=" + serverKey)
                    .url(url)
                    .post(body)
                    .build()
                okHttpClient?.newCall(request)?.enqueue(object : Callback {
                    override fun onFailure(request: Request?, e: IOException?) {
                    }
                    override fun onResponse(response: Response?) {
                        println(response?.body()?.string())
                    }
                })
            }
        }
    }
}