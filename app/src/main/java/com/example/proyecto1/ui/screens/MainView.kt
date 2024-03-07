package com.example.proyecto1.ui.screens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.R
import com.example.proyecto1.ui.myComponents.BottomBar
import com.example.proyecto1.ui.myComponents.ListClientes
import com.example.proyecto1.ui.myComponents.ListServicios
import com.example.proyecto1.ui.myComponents.ListVehículos
import com.example.proyecto1.ui.myComponents.TopBar

@Composable
fun MainView(modifier: Modifier = Modifier,
             viewModel: ActivityViewModel,
             tipoPantalla: String,
             navController: NavController
) {
    val tipo by remember {
        mutableStateOf(tipoPantalla)
    }

    var selectedScreen by remember {
        mutableIntStateOf(0)
    }

    // Componente layout para incluir barra superior, inferior y botón flotante
    Scaffold (
        // Barra superior
        topBar = {
            when (tipo) {
                "Servicios" -> TopBar(title = stringResource(id = R.string.ServicesTitle), showSettings = true, navController = navController)
                "Vehículos" -> TopBar(title = stringResource(id = R.string.VehiclesTitle), showSettings = true, navController = navController)
                "Clientes" -> TopBar(title = stringResource(id = R.string.ClientsTitle), showSettings = true, navController = navController)
            }
        },
        // Barra inferior
        bottomBar = {
            BottomBar(navController = navController, selectedScreen = selectedScreen)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Get the current route (current screen)
                val currentRoute = navController.currentDestination?.route
                Log.d("Routing", "Current route: $currentRoute")

                // Change screen depending on currentRoute (current screen)
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
            selectedScreen = 0
            ListServicios(innerPadding = innerPadding, viewModel = viewModel, navController = navController)
        }
        else if (tipo == "Vehículos") {
            selectedScreen = 1
            ListVehículos(innerPadding = innerPadding, viewModel = viewModel, navController = navController)
        }
        else if (tipo == "Clientes") {
            selectedScreen = 2
            ListClientes(innerPadding = innerPadding, viewModel = viewModel, navController = navController)
        }
    }
}