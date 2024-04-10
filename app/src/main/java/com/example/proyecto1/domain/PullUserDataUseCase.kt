package com.example.proyecto1.domain

import com.example.proyecto1.data.repositories.AppUserRepository
import com.example.proyecto1.data.repositories.ClienteRepository
import com.example.proyecto1.data.repositories.ServicioRepository
import com.example.proyecto1.data.repositories.VehiculoRepository
import javax.inject.Inject

/**
 * Este caso de uso se da cuando un usuario de la aplicación (taller o cliebte) inicia sesión
 * y se descagran de la base de datos remota todos sus archivos a la base de datos local.
 */
class PullUserDataUseCase @Inject constructor(
    val clienteRepository: ClienteRepository,
    val vehiculoRepository: VehiculoRepository,
    val servicioRepository: ServicioRepository,
    val appUserRepository: AppUserRepository
) {
    suspend fun pullUserData(username: String) {
        deleteLocalDBData()

        val clientes = appUserRepository.getClientsFromUser(username)
        for (cliente in clientes) {
            clienteRepository.insertarCliente(cliente)
            val vehicles = clienteRepository.remoteGetClientVehicles(cliente.nombre)
            for (vehicle in vehicles) {
                vehiculoRepository.insertVehiculo(vehicle)
                val servicios = vehiculoRepository.remoteGetVehicleServices(vehicle.matricula)
                for (servicio in servicios) {
                    servicioRepository.insertServicio(servicio)
                }
            }
        }
    }

    private suspend fun deleteLocalDBData() {
        clienteRepository.deleteAllLocalClientes()
        vehiculoRepository.deleteAllLocalVehiculos()
        servicioRepository.deleteAllLocalServicios()
    }
}