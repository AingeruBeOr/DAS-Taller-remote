package com.example.proyecto1.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.ui.screens.AddCliente
import com.example.proyecto1.ui.screens.AddServicio
import com.example.proyecto1.ui.screens.AddVehiculo
import com.example.proyecto1.ui.screens.ListClientes
import com.example.proyecto1.ui.screens.ListServicios
import com.example.proyecto1.ui.screens.ListVehículos
import com.example.proyecto1.ui.screens.Login
import com.example.proyecto1.ui.screens.Preferencias
import com.example.proyecto1.ui.screens.ViewCliente
import com.example.proyecto1.ui.screens.ViewService
import com.example.proyecto1.ui.screens.ViewVehiculo

/**
 * Este Composable implementa un NavHost que se utiliza para la navegación entre pantallas. Este
 * elemento define el gráfico de navegación de la actividad (de la aplicación en este caso).
 *
 * Aquí se definien varias rutas que se llamarán mediante la clase NavHostController. Este elemento
 * permite gestionar la navegación entre las pantallas.
 */
@Composable
fun AppNavigation(
    viewModel: ActivityViewModel,
    openDial: (Int) -> Unit,
    mailTo: (String) -> Unit,
    changeLocales: (String) -> Unit,
    innerPadding: PaddingValues,
    navController: NavHostController,
    changeColor: (String) -> Unit
) {
    // Defining NavHost. This is the navigation graph
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(
                innerPadding = innerPadding
            )
        }
        composable("servicios") {
            ListServicios(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = viewModel)
        }
        composable("vehiculos") {
            ListVehículos(
                innerPadding = innerPadding,
                viewModel = viewModel,
                navController = navController)
        }
        composable("clientes") {
            ListClientes(
                innerPadding = innerPadding,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("newServicio") {
            AddServicio(
                navController = navController,
                viewModel = viewModel,
                innerPadding = innerPadding
            )
        }
        composable("newVehiculo") {
            AddVehiculo(
                navController = navController,
                viewModel = viewModel,
                innerPadding = innerPadding
            )
        }
        composable("newCliente") {
            AddCliente(
                navController = navController,
                viewModel = viewModel,
                innerPadding = innerPadding
            )
        }
        composable("preferencias") {
            Preferencias(
                navController = navController,
                changeLocale = changeLocales,
                innerPadding = innerPadding,
                changeTheme = changeColor,
                viewModel = viewModel
            )
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
                sendMail = mailTo,
                innerPadding = innerPadding
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
                matricula = it.arguments?.getString("matricula"),
                innerPadding = innerPadding)
        }
        composable(
            "viewServicio/{fecha}/{matricula}",
            arguments = listOf(
                navArgument(name = "fecha") {type = NavType.StringType},
                navArgument(name = "matricula") {type = NavType.StringType}
            )
        ) {
            ViewService(
                viewModel = viewModel,
                fecha = it.arguments?.getString("fecha")?.replace("-", "/"),
                matricula = it.arguments?.getString("matricula"),
                innerPadding = innerPadding
            )
        }
    }
}