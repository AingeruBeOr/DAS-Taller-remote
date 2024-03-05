package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.ClienteDao
import com.example.proyecto1.data.database.entities.Cliente
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    val clienteDao: ClienteDao
) {
    fun getAllClientes(): Flow<List<Cliente>> {
        return clienteDao.getAllClientes()
    }

    fun getClientVehicles(nombreCliente: String): List<String> {
        return clienteDao.getClientVehicles(nombreCliente)
    }

    suspend fun getClientInfoFromName(nombreCliente: String): Cliente {
        return clienteDao.getClientInfoFromName(nombreCliente)
    }

    suspend fun insertarCliente(cliente: Cliente) {
        clienteDao.insertCliente(cliente)
    }

    suspend fun deleteCliente(cliente: Cliente) {
        clienteDao.deleteCliente(cliente)
    }
}