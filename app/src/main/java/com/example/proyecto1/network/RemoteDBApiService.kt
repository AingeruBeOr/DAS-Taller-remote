package com.example.proyecto1.network

import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
        @Body servicio: Servicio,
        @Query("taller") taller: String
    ) : Message

    @POST("addVehicle") // Resolved to: http://BASE_URL/addVehicle (Body: JSONified Vehiculo)
    suspend fun addVehicle(
        @Body vehiculo: Vehiculo
    ) : Message

    @POST("addClient") // Resolved to: http://BASE_URL/addClient?username={username} (Body: JSONified Client)
    suspend fun addClient(
        @Query("username") username: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Body cliente: Cliente
    ) : Message

    @POST("FCMdevice") // Resolved to: http://BASE_URL/FCMdevice?token={token}
    suspend fun submitFCMTokenDevice(
        @Query("token") token: String
    )

    @Multipart
    @POST("vehicleDocumentation")
    suspend fun uploadVehicleDocumentation(
        @Query("matricula") matricula: String,
        @Part image: MultipartBody.Part
    )

    @GET("vehicleHasDocumentation")
    suspend fun vehicleHasDocumentation(
        @Query("matricula") matricula: String
    ) : Message

    @GET("getVehicleDocumentation")
    suspend fun getVehicleDocumentation(
        @Query("matricula") matricula: String
    ) : ResponseBody

    @DELETE("deleteVehicle")
    suspend fun deleteVehicle(
        @Query("matricula") matricula: String
    )

    @DELETE("deleteClient")
    suspend fun deleteClient(
        @Query("nombre") nombre: String
    )

    @DELETE("deleteService")
    suspend fun deleteService(
        @Query("fecha") fecha: String,
        @Query("matricula") matricula: String,
        @Query("taller") taller: String
    )

    @GET("clientsLocations")
    suspend fun getClientLocations(
        @Query("user") user: String
    ) : List<ClientLocation>
}
