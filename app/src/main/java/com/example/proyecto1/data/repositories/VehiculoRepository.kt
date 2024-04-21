package com.example.proyecto1.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.proyecto1.data.database.dao.VehiculoDao
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.network.RemoteDBApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Este repositorio se encarga de recoger y enviar datos sobre los vehículos
 */
class VehiculoRepository @Inject constructor(
    val vehiculoDao: VehiculoDao,
    val remoteDBApiService: RemoteDBApiService
) {
    fun getAllVehiculos(): Flow<List<Vehiculo>> {
        return vehiculoDao.getAllVehiculos()
    }

    suspend fun getInfoFromMatricula(matricula: String): Vehiculo {
        return vehiculoDao.getInfoFromMatricula(matricula)
    }

    fun getVehicleServices(matricula: String) : Flow<List<Servicio>> {
        return vehiculoDao.getServicesFromVehiculo(matricula)
    }

    suspend fun remoteGetVehicleServices(matricula: String): List<Servicio> {
        return remoteDBApiService.getVehicleServices(matricula)
    }

    suspend fun insertVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.insertVehiculo(vehiculo)
    }

    suspend fun insertRemoteVehiculo(vehiculo: Vehiculo) {
        remoteDBApiService.addVehicle(vehiculo)
    }

    suspend fun deleteVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.deleteVehiculo(vehiculo)
        remoteDBApiService.deleteVehicle(vehiculo.matricula)
    }

    suspend fun deleteAllLocalVehiculos() {
        vehiculoDao.deleteAll()
    }

    suspend fun uploadVehicleDocumentation(matricula: String, file: File) {
        remoteDBApiService.uploadVehicleDocumentation(
            matricula = matricula,
            image = MultipartBody.Part.createFormData("image", file.name, file.asRequestBody())
        )
    }

    suspend fun vehicleHasDocumentation(matricula: String) : Boolean {
        return remoteDBApiService.vehicleHasDocumentation(matricula).message == "true"
    }

    suspend fun getVehicleDocumentation(matricula: String) : Bitmap {
        val responseBody = remoteDBApiService.getVehicleDocumentation(matricula)
        return responseBody.byteStream().use(BitmapFactory::decodeStream)
    }
}