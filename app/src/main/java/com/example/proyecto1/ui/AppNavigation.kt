package com.example.proyecto1.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto1.ActivityViewModel
import com.example.proyecto1.ui.screens.AddCliente
import com.example.proyecto1.ui.screens.AddServicio
import com.example.proyecto1.ui.screens.AddVehiculo
import com.example.proyecto1.ui.screens.ListClientes
import com.example.proyecto1.ui.screens.ListServicios
import com.example.proyecto1.ui.screens.ListVehículos
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
    innerPadding: PaddingValues,
    navController: NavHostController,
    changeColor: (String) -> Unit
) {
    // Defining NavHost. This is the navigation graph
    NavHost(navController = navController, startDestination = "servicios") {
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