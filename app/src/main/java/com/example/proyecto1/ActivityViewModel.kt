package com.example.proyecto1

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.data.repositories.ClienteRepository
import com.example.proyecto1.data.repositories.ServicioRepository
import com.example.proyecto1.data.repositories.VehiculoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * HILT (Dependency Injection) Explanation:
 *
 * Este view model necesita crear los siguientes objetos:
 * - ClienteRepository
 * - VehiculoRepository
 * - ServicioRepository
 *
 * Una manera sería, dentro de la clase, crear un objetos respository. Sin embargo, estos necesitan
 * sus DAOs respectivos en este caso. Por ese motivo y para evitar que el ViewModel se encargue de
 * ello, se lo de dejamos en manos de HILT.
 *
 * HILT por su parte, injecta las dependencias necesarias las cuales recibirá el ViewModel como
 * parámetros.
 *
 */

@HiltViewModel
class ActivityViewModel @Inject constructor(
    val clienteRepository: ClienteRepository,
    val servicioRepository: ServicioRepository,
    val vehiculoRepository: VehiculoRepository
) : ViewModel() {
    val servicios = servicioRepository.getAllServicios()
    val vehiculos = vehiculoRepository.getAllVehiculos()
    var clientes = clienteRepository.getAllClientes()

    fun addNewServicio(nuevoServicio: Servicio) {
        viewModelScope.launch {
            servicioRepository.insertServicio(nuevoServicio)
        }
    }

    fun deleteServicio(servicioParaBorrar: Servicio) {
        Log.d("ViewModel", "Deleting service")
        viewModelScope.launch{
            servicioRepository.deleteServicio(servicioParaBorrar)
        }
    }

    fun addNewVehiculo(nuevoCoche: Vehiculo) {
        viewModelScope.launch {
            vehiculoRepository.insertVehiculo(nuevoCoche)
        }
    }

    fun deleteVehiculo(vehiculoParaBorrar: Vehiculo) {
        viewModelScope.launch {
            vehiculoRepository.deleteVehiculo(vehiculoParaBorrar)
        }
    }

    fun addNewCliente(nuevoCliente: Cliente) {
        // Launch must be used becase insertarCliente() from repository is a suspend function
        viewModelScope.launch {
            clienteRepository.insertarCliente(nuevoCliente)
        }
    }

    fun deleteCliente(clienteParaBorrar: Cliente) {
        Log.d("ViewModel", "Deleting client: ${clienteParaBorrar.nombre}")
        viewModelScope.launch {
            clienteRepository.deleteCliente(clienteParaBorrar)
        }
    }

    fun getClientVehicles(nombreCliente: String) {
        clienteRepository.getClientVehicles(nombreCliente)
    }
}