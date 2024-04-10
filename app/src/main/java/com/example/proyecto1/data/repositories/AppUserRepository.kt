package com.example.proyecto1.data.repositories

import android.util.Log
import com.example.proyecto1.network.RemoteDBApiService
import javax.inject.Inject

class AppUserRepository @Inject constructor(
    val remoteDBApiService: RemoteDBApiService
){
    suspend fun login(username: String, password: String): String {
        var message = remoteDBApiService.login(username, password)
        message = message.split(':')[1]
            .replace("\"", "")
            .replace("}", "")
        Log.d("Remote response", message)
        return message
    }
}