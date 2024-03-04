package com.example.proyecto1

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.data.model.Cliente
import com.example.proyecto1.data.model.Servicio
import com.example.proyecto1.data.model.Vehiculo
import com.example.proyecto1.data.repositories.ClienteRepository
import kotlinx.coroutines.launch

class ActivityViewModel (
    val clienteRepository: ClienteRepository
) : ViewModel() {
    val servicios = mutableStateListOf<Servicio>()
    val vehiculos = mutableStateListOf<Vehiculo>()
    val clientes = mutableStateListOf<Cliente>()

    fun addNewServicio(nuevoServicio: Servicio) {
        this.servicios.add(nuevoServicio)
    }

    fun deleteServicio(servicioParaBorrar: Servicio) {
        this.servicios.remove(servicioParaBorrar)
    }

    fun addNewVehiculo(nuevoCoche: Vehiculo) {
        this.vehiculos.add(nuevoCoche)
    }

    fun deleteVehiculo(vehiculoParaBorrar: Vehiculo) {
        this.vehiculos.remove(vehiculoParaBorrar)
    }

    fun addNewCliente(nuevoCliente: Cliente) {
        this.clientes.add(nuevoCliente)
    }

    fun addNewCliente2(nuevoCliente: Cliente) {
        viewModelScope.launch {
            clienteRepository.insertarCliente(nuevoCliente)
        }
    }

    fun deleteCliente(clienteParaBorrar: Cliente) {
        Log.d("Data", "Deleting client")
        this.clientes.remove(clienteParaBorrar)
    }
}