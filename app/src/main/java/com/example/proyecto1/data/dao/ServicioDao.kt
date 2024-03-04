package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicios")
    fun getAllServicios(): Flow<List<Servicio>>

    @Insert
    suspend fun insertServicio(servicio: Servicio)

    @Delete
    suspend fun deleteServicio(servicio: Servicio)
}