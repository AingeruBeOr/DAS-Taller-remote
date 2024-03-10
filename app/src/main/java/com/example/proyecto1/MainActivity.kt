package com.example.proyecto1

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.proyecto1.data.database.AppDatabase
import com.example.proyecto1.ui.theme.Proyecto1Theme
import com.example.proyecto1.ui.myComponents.TopBar
import com.example.proyecto1.ui.myComponents.BottomBar
import com.example.proyecto1.ui.myComponents.ListClientes
import com.example.proyecto1.ui.myComponents.ListServicios
import com.example.proyecto1.ui.myComponents.ListVehículos
import com.example.proyecto1.ui.screens.AddCliente
import com.example.proyecto1.ui.screens.AddServicio
import com.example.proyecto1.ui.screens.AddVehiculo
import com.example.proyecto1.ui.screens.MainView
import com.example.proyecto1.ui.screens.Preferencias
import com.example.proyecto1.ui.screens.ViewCliente
import com.example.proyecto1.ui.screens.ViewService
import com.example.proyecto1.ui.screens.ViewVehiculo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KFunction1

/**
 * AppCompatActivity extends FragmentActivity which extends ComponentActivity.
 *
 * ComponentActivity has all you need for a Compose-only app.
 * If you need AppCompat APIs, an AndroidView which works with AppCompat or MaterialComponents
 * theme, or you need Fragments then use AppCompatActivity.
 *
 * Además, nosotros vamos a usar AppCompatActiviy para poder usar Hilt (Inyección de dependencias)
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Re-created activities receive the same ActivityViewModel.kt instance created by the first activity.
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO quitar esto, igual se queda guardado
        runBlocking {
            Log.d("Language", "MainActivity onCreate(): DataStore language is ${viewModel.getSavedLanguage()}")
            changeLocales(viewModel.getSavedLanguage())
        }

        setContent {
            Proyecto1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        viewModel = viewModel,
                        openDial = ::openDial,
                        mailTo = ::mailTo,
                        changeLocales = ::changeLocales
                    )
                }
            }
        }
    }

    fun openDial(tel: Int) {
        // Crear el Intent para abrir el Dial con el número del cliente
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$tel")
        }

        // Iniciamos la actividad que abre el Dial
        startActivity(dialIntent)
    }

    fun mailTo(mail: String) {
        val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            Log.d("Intent", "dirección de mail: $mail")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        }

        startActivity(mailIntent)
    }

    fun changeLocales(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))

        // Done here has saveLanguage is a suspend function
        lifecycleScope.launch {
            viewModel.saveLanguage(languageCode)
        }
    }
}

@Composable
fun AppNavigation(
    viewModel: ActivityViewModel,
    openDial: (Int) -> Unit,
    mailTo: (String) -> Unit,
    changeLocales: (String) -> Unit
) {
    // Defining NavController
    val navController = rememberNavController()

    // Defining NavHost. This is the navigation graph
    NavHost(navController = navController, startDestination = "servicios") {
        composable("servicios") {
            MainView(viewModel = viewModel, tipoPantalla = "Servicios", navController = navController)
        }
        composable("vehiculos") {
            MainView(viewModel = viewModel, tipoPantalla = "Vehículos", navController = navController)
        }
        composable("clientes") {
            MainView(viewModel = viewModel, tipoPantalla = "Clientes", navController = navController)
        }
        composable("newServicio") {
            AddServicio(navController, viewModel)
        }
        composable("newVehiculo") {
            AddVehiculo(navController, viewModel)
        }
        composable("newCliente") {
            AddCliente(navController, viewModel)
        }
        composable("preferencias") {
            Preferencias(navController = navController, changeLocale = changeLocales)
        }
        composable(
            "viewCliente/{nombreCliente}",
            arguments = listOf(navArgument(name = "nombreCliente") {
                type = NavType.StringType
            })
        ) {
            ViewCliente(
                navController = navController,
                viewModel = viewModel,
                nombreCliente = it.arguments?.getString("nombreCliente"),
                openDial = openDial,
                sendMail = mailTo
            )
        }
        composable(
            "viewVehiculo/{matricula}",
            arguments = listOf(navArgument(name = "matricula") {
                type = NavType.StringType
            })
        ) {
            ViewVehiculo(
                navController = navController,
                viewModel = viewModel,
                matricula = it.arguments?.getString("matricula"))
        }
        composable(
            "viewServicio/{fecha}",
            arguments = listOf(navArgument(name = "fecha") {
                type = NavType.StringType
            })
        ) {
            ViewService(
                navController = navController,
                viewModel = viewModel,
                fecha = it.arguments?.getString("fecha")?.replace("-", "/")
            )
        }
    }
}

/**
@Preview(showBackground = true)
@Composable
fun ServiciosPreview() {
    val modifier = Modifier.fillMaxSize()
    val viewModel = ActivityViewModel()
    viewModel.addNewServicio(Servicio(fecha = "2020-12-12", descripcion = "Hola", matricula = "1234"))
    viewModel.addNewServicio(Servicio(fecha = "2020-12-12", descripcion = "Hola", matricula = "1234"))
    viewModel.addNewServicio(Servicio(fecha = "2020-12-12", descripcion = "Hola", matricula = "1234"))
    viewModel.addNewServicio(Servicio(fecha = "2020-12-12", descripcion = "Hola", matricula = "1234"))
    viewModel.addNewServicio(Servicio(fecha = "2020-12-12", descripcion = "Hola", matricula = "1234"))

    Proyecto1Theme {
        MainView(modifier, viewModel, "Servicios", navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun VehiculosPreview() {
    val modifier = Modifier.fillMaxSize()
    val viewModel = ActivityViewModel()
    viewModel.addNewVehiculo(Vehiculo(matricula = "1234", marca = "Mercedes", modelo ="A45 AMG"))
    viewModel.addNewVehiculo(Vehiculo(matricula = "1234", marca = "Mercedes", modelo ="A45 AMG"))
    viewModel.addNewVehiculo(Vehiculo(matricula = "1234", marca = "Mercedes", modelo ="A45 AMG"))
    viewModel.addNewVehiculo(Vehiculo(matricula = "1234", marca = "Mercedes", modelo ="A45 AMG"))
    viewModel.addNewVehiculo(Vehiculo(matricula = "1234", marca = "Mercedes", modelo ="A45 AMG"))

    Proyecto1Theme {
        MainView(modifier, viewModel, "Vehículos", navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ClientesPreview() {
    val modifier = Modifier.fillMaxSize()
    val viewModel = ActivityViewModel()
    viewModel.addNewCliente(Cliente(email = "angel@upv.ehu", nombre = "Angel", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "j.carlos@upv.ehu", nombre = "Juan Carlos", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "jose@upv.ehu", nombre = "Jose", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "pedro@upv.ehu", nombre = "Pedro", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "antton@upv.ehu", nombre = "Antton", telefono = 123456789))

    Proyecto1Theme {
        MainView(modifier, viewModel, "Clientes", navController = rememberNavController())
    }
}*/