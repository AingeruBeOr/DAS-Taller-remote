package com.example.proyecto1.data.repositories

import android.util.Log
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.network.Message
import com.example.proyecto1.network.RemoteDBApiService
import javax.inject.Inject

class AppUserRepository @Inject constructor(
    val remoteDBApiService: RemoteDBApiService
){
    suspend fun login(username: String, password: String): String {
        val response = remoteDBApiService.login(username, password)
        return response.message
    }

    suspend fun registerTaller(username: String, password: String) : String {
        val response = remoteDBApiService.registerTaller(username, password, "taller")
        return response.message
    }

    suspend fun getClientsFromUser(username: String) : List<Cliente> {
        val response = remoteDBApiService.getClientsFromUser(username)
        return response
    }

    suspend fun getUserType(username: String): String {
        return remoteDBApiService.getUserType(username).message
    }

    suspend fun addAppToken(token: String) {
        remoteDBApiService.submitFCMTokenDevice(token)
    }
}