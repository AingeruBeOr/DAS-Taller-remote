package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.dao.ClienteDao
import com.example.proyecto1.data.model.Cliente
import kotlinx.coroutines.flow.Flow

class ClienteRepository (
    val clienteDao: ClienteDao
) {
    fun getAllClientes(): Flow<List<Cliente>> {
        return clienteDao.getAllClientes()
    }

    suspend fun insertarCliente(cliente: Cliente) {
        clienteDao.insertCliente(cliente)
    }

    suspend fun deleteCliente(cliente: Cliente) {
        clienteDao.deleteCliente(cliente)
    }
}