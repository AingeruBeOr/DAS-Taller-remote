package com.example.proyecto1.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) que accede a la base de datos para realizar transacciones sobre los
 * vehículos
 */
@Dao
interface VehiculoDao {
    @Query("SELECT * FROM vehiculos")
    fun getAllVehiculos(): Flow<List<Vehiculo>>

    @Query("SELECT * FROM vehiculos WHERE vehiculos.matricula = :matricula")
    suspend fun getInfoFromMatricula(matricula: String): Vehiculo

    @Query("SELECT * FROM servicios WHERE servicios.matricula = :matricula")
    fun getServicesFromVehiculo(matricula: String): Flow<List<Servicio>>

    @Insert
    suspend fun insertVehiculo(vehiculo: Vehiculo)

    @Delete
    suspend fun deleteVehiculo(vehiculo: Vehiculo)

    @Query("DELETE FROM vehiculos")
    suspend fun deleteAll()
}