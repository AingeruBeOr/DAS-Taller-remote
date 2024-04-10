package com.example.proyecto1.network

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDBApiService{
    @GET("clients")
    suspend fun getClients(): String

    @GET("login") // Resolved to: http://BASE_URL/login?username={username}&password={password}
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ) : String

    @GET("register") // Resolved to: http://BASE_URL/register?username={username}&password={password}&tipo={tipo}
    suspend fun registerTaller(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("tipo") tipo: String
    ) : String
}
