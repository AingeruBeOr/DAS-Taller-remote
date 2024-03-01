package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.ui.theme.Proyecto1Theme


data class Servicio(
    val fecha: String,
    val descripcion: String,
    val matricula: String,
)

data class Vehiculo(
    val matricula: String,
    val marca: String,
    val modelo: String
)

data class Cliente(
    val nombre: String,
    val telefono: Int,
    val email: String
)

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
fun MainView(modifier: Modifier = Modifier, viewModel: ActivityViewModel, tipo_pantalla: String) {
    val tipo by remember {
        mutableStateOf(tipo_pantalla)
    }

    // Componente layout para incluir barra superior, inferior y botón flotante
    Scaffold (
        // Barra superior
        topBar = {
            TopBar(tipo)
        },
        // Barra inferior
        bottomBar = {
            BottomBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
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
fun ListServicios(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (servicio in viewModel.servicios) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = servicio.descripcion)
                        Text(text = servicio.fecha)
                        Text(text = servicio.matricula)
                    }
                }
            }
        }
    }
}

@Composable
fun ListVehículos(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (vehiculo in viewModel.vehiculos) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = vehiculo.marca)
                        Text(text = vehiculo.modelo)
                        Text(text = vehiculo.matricula)
                    }
                }
            }
        }
    }
}

@Composable
fun ListClientes(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: ActivityViewModel) {
    LazyColumn(modifier = modifier.padding(innerPadding)){
        for (cliente in viewModel.clientes) {
            item {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column {
                        Text(text = cliente.nombre)
                        Text(text = cliente.email)
                        Text(text = cliente.telefono.toString())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Ajustes")
            }
        }
    )
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    BottomAppBar (actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Build, contentDescription = "Servicios")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Home, contentDescription = "Vehículos")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Person, contentDescription = "Clientes")
        }
    })
}

@Composable
fun AppNavigation(viewModel: ActivityViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "servicios") {
        composable("servicios") { MainView(viewModel = viewModel, tipo_pantalla = "Servicios") }
        composable("vehículos") { MainView(viewModel = viewModel, tipo_pantalla = "Vehículos") }
        composable("Clientes") { MainView(viewModel = viewModel, tipo_pantalla = "Clientes") }
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
        MainView(modifier, viewModel, "Servicios")
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
        MainView(modifier, viewModel, "Vehículos")
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
        MainView(modifier, viewModel, "Clientes")
    }
}