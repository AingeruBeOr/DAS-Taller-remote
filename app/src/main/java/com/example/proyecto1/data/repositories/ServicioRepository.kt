package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.ServicioDao
import com.example.proyecto1.data.database.entities.Servicio
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Este repositorio se encarga de recoger y enviar datos sobre los servicios
 */
class ServicioRepository @Inject constructor (
    val servicioDao: ServicioDao
) {
    fun getAllServicios(): Flow<List<Servicio>> = servicioDao.getAllServicios()

    suspend fun getAllServiciosAsList(): List<Servicio> = servicioDao.getAllServiciosAsList()

    suspend fun getServicioFromFechaMatricula(
        fecha: String,
        matricula: String
    ) : Servicio = servicioDao.getServicioFromFechaMatricula(fecha, matricula)

    suspend fun insertServicio(servicio: Servicio) {
        servicioDao.insertServicio(servicio)
    }

    suspend fun deleteServicio(servicio: Servicio) {
        servicioDao.deleteServicio(servicio)
    }
}