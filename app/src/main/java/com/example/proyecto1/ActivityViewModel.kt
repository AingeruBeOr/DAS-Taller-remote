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