package com.example.proyecto1.data.repositories

import android.util.Log
import com.example.proyecto1.network.RemoteDBApiService
import javax.inject.Inject

class AppUserRepository @Inject constructor(
    val remoteDBApiService: RemoteDBApiService
){
    suspend fun login(username: String, password: String): String {
        val response = remoteDBApiService.login(username, password)
        return getResponseMessage(response)
    }

    suspend fun registerTaller(username: String, password: String) : String {
        val response = remoteDBApiService.registerTaller(username, password, "taller")
        return getResponseMessage(response)
    }

    private fun getResponseMessage(fullResponse: String) : String {
        // TODO tiene que haber una maner más correcta
        val message = fullResponse.split(':')[1]
            .replace("\"", "")
            .replace("}", "")
        Log.d("Remote response", message)
        return message
    }
}