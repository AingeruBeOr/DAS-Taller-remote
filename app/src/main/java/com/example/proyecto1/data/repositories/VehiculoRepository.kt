package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.VehiculoDao
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Este repositorio se encarga de recoger y enviar datos sobre los vehículos
 */
class VehiculoRepository @Inject constructor(
    val vehiculoDao: VehiculoDao
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

    suspend fun insertVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.insertVehiculo(vehiculo)
    }

    suspend fun deleteVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.deleteVehiculo(vehiculo)
    }
}