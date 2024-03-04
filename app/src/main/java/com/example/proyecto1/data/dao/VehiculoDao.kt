package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Vehiculo

@Dao
interface VehiculoDao {
    @Query("SELECT * FROM vehiculos")
    fun getAllVehiculos(): List<Vehiculo>

    @Insert
    fun insertVehiculo(vehiculo: Vehiculo)

    @Delete
    fun deleteVehiculo(vehiculo: Vehiculo)
}