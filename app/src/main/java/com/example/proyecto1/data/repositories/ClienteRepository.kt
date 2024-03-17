package com.example.proyecto1.data.repositories

import com.example.proyecto1.data.database.dao.ClienteDao
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Vehiculo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Este repositorio se encarga de recoger y enviar datos sobre los clientes
 */
class ClienteRepository @Inject constructor(
    val clienteDao: ClienteDao
) {
    fun getAllClientes(): Flow<List<Cliente>> {
        return clienteDao.getAllClientes()
    }

    fun getClientVehicles(nombreCliente: String): Flow<List<Vehiculo>> {
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