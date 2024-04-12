package com.example.proyecto1.network

import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteDBApiService{
    @GET("login") // Resolved to: http://BASE_URL/login?username={username}&password={password}
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ) : Message

    @GET("register") // Resolved to: http://BASE_URL/register?username={username}&password={password}&tipo={tipo}
    suspend fun registerTaller(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("tipo") tipo: String
    ) : Message

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

    @GET("userType") // Resolved to: http://BASE_URL/userType?username={username}
    suspend fun getUserType(
        @Query("username") username: String
    ) : Message

    @POST("addService") // Resolved to: http://BASE_URL/addService (Body: JSONified Servicio)
    suspend fun addService(
        @Body servicio: Servicio
    ) : Message

    @POST("addVehicle") // Resolved to: http://BASE_URL/addVehicle (Body: JSONified Vehiculo)
    suspend fun addVehicle(
        @Body vehiculo: Vehiculo
    ) : Message

    @POST("addClient") // Resolved to: http://BASE_URL/addClient?username={username} (Body: JSONified Client)
    suspend fun addClient(
        @Query("username") username: String,
        @Body cliente: Cliente
    ) : Message
}
