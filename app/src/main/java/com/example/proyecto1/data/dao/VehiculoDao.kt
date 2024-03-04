package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Vehiculo
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiculoDao {
    @Query("SELECT * FROM vehiculos")
    fun getAllVehiculos(): Flow<List<Vehiculo>>

    @Insert
    suspend fun insertVehiculo(vehiculo: Vehiculo)

    @Delete
    suspend fun deleteVehiculo(vehiculo: Vehiculo)
}