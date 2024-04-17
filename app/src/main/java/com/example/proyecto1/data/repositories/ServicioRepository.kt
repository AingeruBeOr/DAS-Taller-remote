package com.example.proyecto1.data.repositories

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.example.proyecto1.data.database.dao.ServicioDao
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.network.RemoteDBApiService
import com.example.proyecto1.ui.widgets.TallerAppWidget
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Este repositorio se encarga de recoger y enviar datos sobre los servicios
 */
class ServicioRepository @Inject constructor (
    val servicioDao: ServicioDao,
    val remoteDBApiService: RemoteDBApiService
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

    suspend fun insertarRemoteServicio(servicio: Servicio) {
        remoteDBApiService.addService(servicio)
    }

    suspend fun deleteServicio(servicio: Servicio) {
        servicioDao.deleteServicio(servicio)
        remoteDBApiService.deleteService(servicio.fecha, servicio.matricula)
    }

    suspend fun deleteAllLocalServicios() {
        servicioDao.deleteAll()
    }

    suspend fun generateWidgetGraph(taller: String) {
        remoteDBApiService.generateWidgetGraph(taller)
    }
}