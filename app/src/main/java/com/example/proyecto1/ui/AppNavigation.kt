package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.ui.screens.AddCliente
import com.example.proyecto1.ui.screens.AddServicio
import com.example.proyecto1.ui.screens.AddVehiculo
import com.example.proyecto1.ui.screens.MainView
import com.example.proyecto1.ui.screens.Preferencias
import com.example.proyecto1.ui.screens.ViewCliente
import com.example.proyecto1.ui.screens.ViewService
import com.example.proyecto1.ui.screens.ViewVehiculo

@Composable
fun AppNavigation(
    viewModel: ActivityViewModel,
    openDial: (Int) -> Unit,
    mailTo: (String) -> Unit,
    changeLocales: (String) -> Unit,
    sendNotification: (String) -> Unit
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
            AddServicio(navController, viewModel, sendNotification)
        }
        composable("newVehiculo") {
            AddVehiculo(navController, viewModel, sendNotification)
        }
        composable("newCliente") {
            AddCliente(navController, viewModel, sendNotification)
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