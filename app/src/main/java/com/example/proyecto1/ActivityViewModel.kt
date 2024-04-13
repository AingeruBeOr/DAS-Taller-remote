package com.example.proyecto1

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.data.repositories.AppUserRepository
import com.example.proyecto1.data.repositories.ClienteRepository
import com.example.proyecto1.data.repositories.PreferencesRepository
import com.example.proyecto1.data.repositories.ServicioRepository
import com.example.proyecto1.data.repositories.VehiculoRepository
import com.example.proyecto1.domain.DownloadMonthServices
import com.example.proyecto1.domain.PullUserDataUseCase
import com.example.proyecto1.network.RemoteDBApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *  --- HILT (Dependency Injection) Explanation ---
 *
 * Este view model necesita crear los siguientes objetos:
 * - ClienteRepository
 * - VehiculoRepository
 * - ServicioRepository
 * - PreferencesRepository
 * - DownloadMonthServices
 *
 * Una manera sería, dentro de la clase, crear un objetos respository. Sin embargo, estos necesitan
 * sus DAOs respectivos en este caso. Por ese motivo y para evitar que el ViewModel se encargue de
 * ello, se lo de dejamos en manos de HILT.
 *
 * HILT por su parte, injecta las dependencias necesarias las cuales recibirá el ViewModel como
 * parámetros.
 *
 *
 * --- Función del ViewModel ---
 *
 * Los ViewModels son clases que permiten geestionar el estado y los eventos de los componentes
 * de la IU. Para esta aplicación, se ha definidio un solo ViewModel para gestionar todos estos
 * estados y eventos aunque se pueden definir varios específicos para diferentes componentes.
 */
@HiltViewModel
class ActivityViewModel @Inject constructor(
    val clienteRepository: ClienteRepository,
    val servicioRepository: ServicioRepository,
    val vehiculoRepository: VehiculoRepository,
    val appUserRepository: AppUserRepository,
    val preferencesRepository: PreferencesRepository,
    val downloadMonthServices: DownloadMonthServices,
    val pullUserDataUseCase: PullUserDataUseCase
) : ViewModel() {
    // Variables que reciben un Flow de la base de datos con los datos actualizados al momento
    val servicios = servicioRepository.getAllServicios()
    val vehiculos = vehiculoRepository.getAllVehiculos()
    var clientes = clienteRepository.getAllClientes()

    private var actualCiente = Cliente("a", 1, "a")
    private var actualVehiculo = Vehiculo(matricula = "a", "a", "a", "a")
    private var actualServicio = Servicio("a", "a", "a")
    var currentTheme = preferencesRepository.getUserTheme()
    var currentThemeSnapshot = "Blue"
    var loginResponse = ""
    var registerResponse = ""
    var currentUserType = mutableStateOf("taller")
    var currentUserName = ""

    fun addNewServicio(nuevoServicio: Servicio) {
        viewModelScope.launch {
            servicioRepository.insertServicio(nuevoServicio)
            servicioRepository.insertarRemoteServicio(nuevoServicio)
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
            vehiculoRepository.insertRemoteVehiculo(nuevoCoche)
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
            clienteRepository.insertarRemoteCliente(nuevoCliente, currentUserName)
        }
    }

    fun deleteCliente(clienteParaBorrar: Cliente) {
        Log.d("ViewModel", "Deleting client: ${clienteParaBorrar.nombre}")
        viewModelScope.launch {
            clienteRepository.deleteCliente(clienteParaBorrar)
        }
    }

    fun getUserDataFromName(nombreCliente: String): Cliente {
        viewModelScope.launch {
            actualCiente = clienteRepository.getClientInfoFromName(nombreCliente)
        }
        return actualCiente
    }

    fun getClientVehicles(nombreCliente: String): Flow<List<Vehiculo>> {
        return clienteRepository.getClientVehicles(nombreCliente)
    }

    fun getVehicleDataFromMatricula(matricula: String): Vehiculo {
        viewModelScope.launch {
            actualVehiculo = vehiculoRepository.getInfoFromMatricula(matricula)
        }
        return actualVehiculo
    }

    fun getVehicleServices(matricula: String): Flow<List<Servicio>> {
        return vehiculoRepository.getVehicleServices(matricula)
    }

    fun getServicioFromFecha(fecha: String, matricula: String) : Servicio {
       viewModelScope.launch {
           actualServicio = servicioRepository.getServicioFromFechaMatricula(fecha, matricula)
       }
       return actualServicio
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadMonthServices() {
        downloadMonthServices.downloadMonthServices()
    }

    fun changeColor(theme: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserTheme(theme)
        }
    }

    fun getThemeSnapshot(): String {
        viewModelScope.launch {
            currentThemeSnapshot = preferencesRepository.getUserThemeSnapshot()
        }
        return currentThemeSnapshot
    }
    fun login(username: String, password: String): String {
        viewModelScope.launch {
            loginResponse = appUserRepository.login(username, password)
        }
        return loginResponse
    }

    fun pullUserData(username: String) {
        viewModelScope.launch {
            pullUserDataUseCase.pullUserData(username)
            currentUserType.value = appUserRepository.getUserType(username)
            currentUserName = username
        }
    }

    fun registerTaller(username: String, password: String) : String {
        viewModelScope.launch {
            registerResponse = appUserRepository.registerTaller(username, password)
        }
        return registerResponse
    }

    fun submitDeviceTokenFCM(token: String) {
        viewModelScope.launch {
            appUserRepository.addAppToken(token)
        }
    }
}