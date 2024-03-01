package com.example.proyecto1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ui.theme.Proyecto1Theme
import com.example.proyecto1.myComponents.TopBar
import com.example.proyecto1.myComponents.BottomBar
import com.example.proyecto1.myComponents.ListClientes
import com.example.proyecto1.myComponents.ListServicios
import com.example.proyecto1.myComponents.ListVehículos
import com.example.proyecto1.screens.AddCliente
import com.example.proyecto1.screens.AddClientePreview
import com.example.proyecto1.screens.AddServicio
import com.example.proyecto1.screens.AddVehiculo


class ActivityViewModel : ViewModel() {
    val servicios = mutableStateListOf<Servicio>()
    val vehiculos = mutableStateListOf<Vehiculo>()
    val clientes = mutableStateListOf<Cliente>()

    fun addNewServicio(nuevoServicio: Servicio) {
        this.servicios.add(nuevoServicio)
    }

    fun addNewVehiculo(nuevoCoche: Vehiculo) {
        this.vehiculos.add(nuevoCoche)
    }

    fun addNewCliente(nuevoCliente: Cliente) {
        this.clientes.add(nuevoCliente)
    }
}


class MainActivity : ComponentActivity() {
    val viewModel = ActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                    //MainView(viewModel = viewModel, tipo_pantalla = "Servicios")
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier,
             viewModel: ActivityViewModel,
             tipoPantalla: String,
             navController: NavController
) {
    val tipo by remember {
        mutableStateOf(tipoPantalla)
    }

    // Componente layout para incluir barra superior, inferior y botón flotante
    Scaffold (
        // Barra superior
        topBar = {
            TopBar(tipo, showSettings = true)
        },
        // Barra inferior
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Get the current route (current screen)
                val currentRoute = navController.currentDestination?.route
                Log.d("Routing", "Current route: $currentRoute")

                // Change screen depending on currentRoute
                when (currentRoute) {
                    "servicios" -> navController.navigate("newServicio")
                    "vehiculos" -> navController.navigate("newVehiculo")
                    "clientes" -> navController.navigate("newCliente")
                }
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Añadir")
            }
        }
    ) {
        // Contenido principal
        innerPadding  ->
        if (tipo == "Servicios") {
            ListServicios(innerPadding = innerPadding, viewModel = viewModel)
        }
        else if (tipo == "Vehículos") {
            ListVehículos(innerPadding = innerPadding, viewModel = viewModel)
        }
        else if (tipo == "Clientes") {
            ListClientes(innerPadding = innerPadding, viewModel = viewModel)
        }
    }
}

@Composable
fun AppNavigation(viewModel: ActivityViewModel) {
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
            AddServicio(navController)
        }
        composable("newVehiculo") {
            AddVehiculo(navController)
        }
        composable("newCliente") {
            AddCliente(navController, viewModel)
        }
    }
}

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
    viewModel.addNewCliente(Cliente(email = "upv@upv.ehu", nombre = "Pepito", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "upv@upv.ehu", nombre = "Pepito", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "upv@upv.ehu", nombre = "Pepito", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "upv@upv.ehu", nombre = "Pepito", telefono = 123456789))
    viewModel.addNewCliente(Cliente(email = "upv@upv.ehu", nombre = "Pepito", telefono = 123456789))

    Proyecto1Theme {
        MainView(modifier, viewModel, "Clientes", navController = rememberNavController())
    }
}