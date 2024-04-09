package com.example.proyecto1.data.repositories

import android.util.Log
import com.example.proyecto1.network.RemoteDBApiService
import javax.inject.Inject

class AppUserRepository @Inject constructor(
    val remoteDBApiService: RemoteDBApiService
){
    suspend fun login(username: String, password: String) {
        //remoteDBApiService.login(username, password)
        // TODO borrar, es una prueba
        val clients = remoteDBApiService.getClients()
        Log.d("Remote response", clients)
    }
}