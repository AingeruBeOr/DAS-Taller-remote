package com.example.proyecto1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto1.data.model.Cliente

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    fun getAllClientes(): List<Cliente>

    @Insert
    fun insertCliente(cliente: Cliente)

    @Delete
    fun deleteCliente(cliente: Cliente)
}