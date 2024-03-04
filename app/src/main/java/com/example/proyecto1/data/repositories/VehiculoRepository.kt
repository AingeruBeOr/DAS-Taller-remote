package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.VehiculoDao
import com.example.proyecto1.data.database.entities.Vehiculo
import kotlinx.coroutines.flow.Flow

class VehiculoRepository(
    val vehiculoDao: VehiculoDao
) {
    fun getAllVehiculos(): Flow<List<Vehiculo>> {
        return vehiculoDao.getAllVehiculos()
    }

    suspend fun insertVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.insertVehiculo(vehiculo)
    }

    suspend fun deleteVehiculo(vehiculo: Vehiculo) {
        vehiculoDao.deleteVehiculo(vehiculo)
    }
}