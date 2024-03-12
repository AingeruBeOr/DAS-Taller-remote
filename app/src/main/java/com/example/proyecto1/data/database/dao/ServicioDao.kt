package com.example.proyecto1.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.database.entities.Servicio
import kotlinx.coroutines.flow.Flow
import java.time.Month

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicios")
    fun getAllServicios(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios")
    suspend fun getAllServiciosAsList(): List<Servicio>

    @Query("SELECT * FROM servicios WHERE servicios.fecha = :fecha")
    suspend fun getServicioFromFecha(fecha: String) : Servicio

    /*@Query("SELECT * " +
            "FROM servicios " +
            "WHERE servicios.fecha >= :firstDate AND servicios.fecha <= :lastDate") // TODO esto no está bien
    fun getMonthServices(firstDate: String, lastDate: String) : List<Servicio>*/

    @Insert
    suspend fun insertServicio(servicio: Servicio)

    @Delete
    suspend fun deleteServicio(servicio: Servicio)
}