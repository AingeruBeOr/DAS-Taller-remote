package com.example.proyecto1

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
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
import com.example.proyecto1.network.ClientLocation
import com.example.proyecto1.ui.widgets.TallerAppWidget
import com.example.proyecto1.ui.widgets.getImageBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
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
    var loginResponse = MutableStateFlow<String>("")
    var registerResponse = ""
    var currentUserType = mutableStateOf("taller")
    var currentUserName = ""
    var vehicleDocumentation = MutableStateFlow<Bitmap?>(null)
    var userClientLocations = MutableStateFlow<List<ClientLocation>>(listOf())

    fun addNewServicio(nuevoServicio: Servicio, context: Context) {
        viewModelScope.launch {
            servicioRepository.insertServicio(nuevoServicio)
            servicioRepository.insertarRemoteServicio(nuevoServicio, currentUserName)
            updateWidget(context)
        }
    }

    fun deleteServicio(servicioParaBorrar: Servicio, context: Context) {
        Log.d("ViewModel", "Deleting service")
        viewModelScope.launch{
            servicioRepository.deleteServicio(servicioParaBorrar, currentUserName)
            updateWidget(context)
        }
    }

    fun addNewVehiculo(nuevoCoche: Vehiculo, file: File) {
        viewModelScope.launch {
            vehiculoRepository.insertVehiculo(nuevoCoche)
            vehiculoRepository.insertRemoteVehiculo(nuevoCoche)
            Log.d("File upload", "file size: ${file.length()}")
            vehiculoRepository.uploadVehicleDocumentation(nuevoCoche.matricula, file)
        }
    }

    fun deleteVehiculo(vehiculoParaBorrar: Vehiculo) {
        viewModelScope.launch {
            vehiculoRepository.deleteVehiculo(vehiculoParaBorrar)
        }
    }

    fun addNewCliente(nuevoCliente: Cliente, latitude: String, longitude: String) {
        // Launch must be used becase insertarCliente() from repository is a suspend function
        viewModelScope.launch {
            clienteRepository.insertarCliente(nuevoCliente)
            clienteRepository.insertarRemoteCliente(
                nuevoCliente,
                currentUserName,
                latitude = latitude,
                longitude = longitude
            )
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
    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginResponse.value = appUserRepository.login(username, password)
        }
    }

    fun loginSuccessful(username: String, context: Context) {
        viewModelScope.launch {
            pullUserDataUseCase.pullUserData(username)
            currentUserType.value = appUserRepository.getUserType(username)
            currentUserName = username
            preferencesRepository.saveLastUserName(username)
            updateWidget(context)
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

    fun getVehicleDocumentation(matricula: String) {
        viewModelScope.launch {
            delay(300) // TODO, esperamos a que cargue la foto
            vehicleDocumentation.value = vehiculoRepository.getVehicleDocumentation(matricula)
            delay(300) // TODO, esperamos a que cargue la foto
        }
    }

    fun getUsersClientLocations(){
        viewModelScope.launch {
            userClientLocations.value = clienteRepository.getUsersClientLocations(currentUserName)
        }
    }

    fun updateWidget(context: Context) {
        viewModelScope.launch {
            // Lo hacemos con withContext es ejecutarlo en un hilo seprado pero no seguir hasta que tengamos el valor
            val imageBitmap = withContext(Dispatchers.IO) {
                context.getImageBitmap("http://34.155.61.4/widgetPlots/${currentUserName}.png")
            }

            // Bitmap to String
            val baos = ByteArrayOutputStream()
            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b : ByteArray = baos.toByteArray()
            val imageString = Base64.encodeToString(b, Base64.DEFAULT)
            Log.d("Widget", "BASE64 image size in VM: ${imageString.length}")

            Log.d("ViewModel", "Updating widget")
            // We get the widget manager
            val manager = GlanceAppWidgetManager(context)
            // We get all the glace IDs that are a TallerAppWidget (remember than we can have more
            // than one widget of the same type)
            val glanceIds = manager.getGlanceIds(TallerAppWidget::class.java)
            // For each glanceIds...
            Log.d("Widget", "Los glanceIds de los widgets son: $glanceIds")
            glanceIds.forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[TallerAppWidget.imageKey] = imageString
                }
            }
            TallerAppWidget().updateAll(context)
        }
    }
}