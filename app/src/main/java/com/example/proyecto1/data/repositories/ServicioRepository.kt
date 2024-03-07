package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.ServicioDao
import com.example.proyecto1.data.database.entities.Servicio
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServicioRepository @Inject constructor (
    val servicioDao: ServicioDao
) {
    fun getAllServicios(): Flow<List<Servicio>> = servicioDao.getAllServicios()

    suspend fun getServicioFromFecha(fecha: String) : Servicio = servicioDao.getServicioFromFecha(fecha)

    suspend fun insertServicio(servicio: Servicio) {
        servicioDao.insertServicio(servicio)
    }

    suspend fun deleteServicio(servicio: Servicio) {
        servicioDao.deleteServicio(servicio)
    }
}