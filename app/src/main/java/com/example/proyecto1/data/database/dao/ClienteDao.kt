package com.example.proyecto1.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    fun getAllClientes(): Flow<List<Cliente>>


    @Query("SELECT matricula FROM vehiculos WHERE vehiculos.nombreCliente == :nombreCliente")
    fun getClientVehicles(nombreCliente: String): List<String>

    @Insert
    suspend fun insertCliente(cliente: Cliente)

    @Delete
    suspend fun deleteCliente(cliente: Cliente)
}