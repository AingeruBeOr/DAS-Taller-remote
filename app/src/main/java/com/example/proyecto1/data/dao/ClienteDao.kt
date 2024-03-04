package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    fun getAllClientes(): Flow<List<Cliente>>

    @Insert
    suspend fun insertCliente(cliente: Cliente)

    @Delete
    suspend fun deleteCliente(cliente: Cliente)
}