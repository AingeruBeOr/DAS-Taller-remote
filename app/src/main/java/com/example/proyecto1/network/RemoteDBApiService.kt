package com.example.proyecto1.network

import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
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

    @GET("clientsFromUser") // Resolved to: http://BASE_URL/clientsFromUser?username={username}
    suspend fun getClientsFromUser(
        @Query("username") username: String
    ) : List<Cliente>

    @GET("clientVehicles") // Resolved to: http://BASE_URL/clientVehicles?client={client}
    suspend fun getClientVehicles(
        @Query("client") client: String
    ) : List<Vehiculo>

    @GET("vehicleServices") // Resolved to: http://BASE_URL/vehicleServices?matricula={matricula}
    suspend fun getVehicleServices(
        @Query("matricula") matricula: String
    ) : List<Servicio>
}
