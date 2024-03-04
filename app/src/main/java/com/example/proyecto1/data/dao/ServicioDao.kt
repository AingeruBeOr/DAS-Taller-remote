package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Servicio

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicios")
    fun getAllServicios(): List<Servicio>

    @Insert
    fun insertServicio(servicio: Servicio)

    @Delete
    fun deleteServicio(servicio: Servicio)
}